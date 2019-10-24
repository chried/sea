import {
  login,
  logout,
  getUserInfo,
  getMessage,
  getContentByMsgId,
  hasRead,
  removeReaded,
  restoreTrash,
  getUnreadCount
} from '@/api/user'
import {setToken, getToken} from '@/libs/util'

export default {
  state: {
    userId: '',
    token: getToken(),
    hasGetInfo: false,
    account: '',
    nickname: '',
    portrait: '',
    email: '',
    roles: [],
    permissions: [],
    messageCount: 0,
    messageUnreadList: [],
    messageReadedList: [],
    messageTrashList: [],
    menus: []
  },
  mutations: {
    setAccount(state, account) {
      state.account = account
    },
    setMenus(state, menus) {
      state.menus = menus
    },
    setMessageCount(state, messageCount) {
      state.messageCount = messageCount
    },
    setEmail(state, email) {
      state.email = email
    },
    setUserId(state, userId) {
      state.userId = userId
    },
    setNickname(state, nickname) {
      state.nickname = nickname
    },
    setPortrait(state, portrait) {
      state.portrait = portrait
    },
    setToken(state, token) {
      state.token = token
      setToken(token)
    },
    setHasGetInfo(state, status) {
      state.hasGetInfo = status
    },
    setRoles(state, roles) {
      state.roles = roles
    },
    setPermissions(state, permissions) {
      state.permissions = permissions
    },
    setMessageUnreadList(state, list) {
      state.messageUnreadList = list
    },
    setMessageReadedList(state, list) {
      state.messageReadedList = list
    },
    setMessageTrashList(state, list) {
      state.messageTrashList = list
    },
    updateMessageContentStore(state, {msg_id, content}) {
      state.messageContentStore[msg_id] = content
    },
    moveMsg(state, {from, to, msg_id}) {
      const index = state[from].findIndex(_ => _.msg_id === msg_id)
      const msgItem = state[from].splice(index, 1)[0]
      msgItem.loading = false
      state[to].unshift(msgItem)
    }
  },
  getters: {
    messageUnreadCount: state => state.messageUnreadList.length,
    messageReadedCount: state => state.messageReadedList.length,
    messageTrashCount: state => state.messageTrashList.length
  },
  actions: {
    // 登录
    handleLogin({commit}, {account, password}) {
      account = account.trim()
      return new Promise((resolve, reject) => {
        login({
          account,
          password
        }).then(res => {
          const data = res.data;
          if (data.code !== 200) {
            reject(data.msg);
            return;
          }
          commit('setToken', data.data);
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 退出登录
    handleLogOut({state, commit}) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('setToken', '')
          commit('setAccess', [])
          resolve()
        }).catch(err => {
          reject(err)
        })
        // 如果你的退出登录无需请求接口，则可以直接使用下面三行代码而无需使用logout调用接口
        // commit('setToken', '')
        // commit('setAccess', [])
        // resolve()
      })
    },
    // 获取用户相关信息
    getUserInfo({state, commit}) {
      return new Promise((resolve, reject) => {
        try {
          getUserInfo(state.token).then(res => {
            const data = res.data
            // 获取用户信息失败，直接提示客户.
            if (data.code !== 200) {
              reject(data)
              return
            }
            commit('setPortrait', data.data.portrait)
            commit('setRoles', data.data.roles)
            commit('setPermissions', data.data.permissions)
            commit('setNickname', data.data.nickname)
            commit('setEmail', data.data.email)
            commit('setUserId', data.data.userId)
            commit('setAccount', data.data.account)
            commit('setMenus', data.data.menus)
            commit('setHasGetInfo', true)
            resolve(data.data)
          }).catch(err => {
            reject(err)
          })
        } catch (error) {
          reject(error)
        }
      })
    },
    // 此方法用来获取未读消息条数，接口只返回数值，不返回消息列表
    getUnreadMessageCount({state, commit}) {
      getUnreadCount().then(res => {
        const {data: {data}} = res
        commit('setMessageCount', data)
      })
    },
    // 获取消息列表，其中包含未读、已读、回收站三个列表
    getMessageList({state, commit}) {
      return new Promise((resolve, reject) => {
        getMessage().then(res => {
          const {unread, readed, trash} = res.data
          commit('setMessageUnreadList', unread.sort((a, b) => new Date(b.create_time) - new Date(a.create_time)))
          commit('setMessageReadedList', readed.map(_ => {
            _.loading = false
            return _
          }).sort((a, b) => new Date(b.create_time) - new Date(a.create_time)))
          commit('setMessageTrashList', trash.map(_ => {
            _.loading = false
            return _
          }).sort((a, b) => new Date(b.create_time) - new Date(a.create_time)))
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 根据当前点击的消息的id获取内容
    getContentByMsgId({state, commit}, {msg_id}) {
      return new Promise((resolve, reject) => {
        let contentItem = state.messageContentStore[msg_id]
        if (contentItem) {
          resolve(contentItem)
        } else {
          getContentByMsgId(msg_id).then(res => {
            const content = res.data
            commit('updateMessageContentStore', {msg_id, content})
            resolve(content)
          })
        }
      })
    },
    // 把一个未读消息标记为已读
    hasRead({state, commit}, {msg_id}) {
      return new Promise((resolve, reject) => {
        hasRead(msg_id).then(() => {
          commit('moveMsg', {
            from: 'messageUnreadList',
            to: 'messageReadedList',
            msg_id
          })
          commit('setMessageCount', state.unreadCount - 1)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 删除一个已读消息到回收站
    removeReaded({commit}, {msg_id}) {
      return new Promise((resolve, reject) => {
        removeReaded(msg_id).then(() => {
          commit('moveMsg', {
            from: 'messageReadedList',
            to: 'messageTrashList',
            msg_id
          })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 还原一个已删除消息到已读消息
    restoreTrash({commit}, {msg_id}) {
      return new Promise((resolve, reject) => {
        restoreTrash(msg_id).then(() => {
          commit('moveMsg', {
            from: 'messageTrashList',
            to: 'messageReadedList',
            msg_id
          })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}
