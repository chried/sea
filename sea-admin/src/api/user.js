import axios from '@/libs/api.request'

import {TOKEN_KEY} from '@/libs/util'

export const login = ({account, password}) => {
  const data = {
    account,
    password
  }
  return axios.request({
    url: 'login',
    data,
    method: 'post'
  })
};

export const getUserInfo = (token) => {
  const token_ = TOKEN_KEY;
  return axios.request({
    url: 'admin/user/getUserInfo',
    headers: {
      "access_token": token
    },
    method: 'get'
  })
};

export const logout = (token) => {
  return axios.request({
    url: 'logout',
    method: 'post'
  })
};

export const getUnreadCount = () => {
  return axios.request({
    url: 'admin/message/count',
    method: 'get'
  })
};

export const getMessage = () => {
  return axios.request({
    url: 'message/init',
    method: 'get'
  })
};

export const getContentByMsgId = msg_id => {
  return axios.request({
    url: 'message/content',
    method: 'get',
    params: {
      msg_id
    }
  })
};

export const hasRead = msg_id => {
  return axios.request({
    url: 'message/has_read',
    method: 'post',
    data: {
      msg_id
    }
  })
};

export const removeReaded = msg_id => {
  return axios.request({
    url: 'message/remove_readed',
    method: 'post',
    data: {
      msg_id
    }
  })
};

export const restoreTrash = msg_id => {
  return axios.request({
    url: 'message/restore',
    method: 'post',
    data: {
      msg_id
    }
  })
};


/**
 * 获取用户列表.
 * @param q 关键字
 * @param size 每页展示.
 * @param current 当前页.
 * @returns {*}
 */
export const getTableData = ({q, size, current}) => {

  const data = {
    q, size, current,
    columns: ['account', 'nickname']
  };

  return axios.request({
    url: '/admin/user/query',
    data,
    method: 'post'
  })
};

/**
 * 处理用户授权角色.
 * @param params 参数.
 */
export const handleUserRole = params => {


  return axios.request({
    url: '/admin/user/handleUserRole',
    data: {
      id: params.userId,
      ids: params.roleIds
    },
    method: 'post'
  })
};
