import axios from '@/libs/api.request'

/**
 * 获取角色列表.
 * @param q 关键字
 * @param size 每页展示.
 * @param current 当前页.
 * @returns {*}
 */
export const getTableData = ({q, size, current}) => {
  const data = {
    q, size, current,
    columns: ['name', 'sign']
  };
  return axios.request({
    url: '/admin/role/query',
    data,
    method: 'post'
  })
};

/**
 * 添加或者编辑角色.
 * @param params 参数.
 */
export const addOrEditRole = params => {
  const {id, name, sign} = params;

  const data = {
    id, name, sign
  };
  return axios.request({
    url: '/admin/role/addOrEdit',
    data,
    method: 'post'
  })
};

/**
 * 删除角色.
 * @param params 参数.
 */
export const deleteRoles = params => {

  const data = {
    ids: params
  };
  return axios.request({
    url: '/admin/role/deleteRoles',
    data,
    method: 'post'
  })
};

/**
 * 查询角色树.
 * @param params 参数.
 */
export const queryRoleTree = params => {

  return axios.request({
    url: '/admin/role/queryRoleTree?userId=' + params.userId,
    method: 'get'
  })
};

/**
 * 处理角色权限.
 * @param params 参数.
 */
export const handleRolePermission = params => {

  return axios.request({
    url: '/admin/role/handleRolePermission',
    data: {
      id: params.id,
      ids: params.ids
    },
    method: 'post'
  })
};

/**
 * 处理角色菜单.
 * @param params 参数.
 */
export const handleRoleMenu = params => {

  const data = {
    id: params.id,
    ids: params.ids
  };
  return axios.request({
    url: '/admin/role/handleRoleMenu',
    data,
    method: 'post'
  })
};

