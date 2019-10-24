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
    url: '/admin/permission/query',
    data,
    method: 'post'
  })
};

/**
 * 添加或者编辑角色.
 * @param params 参数.
 */
export const addOrEditPermission = params => {
  const {id, name, sign, url} = params;

  const data = {
    id, name, sign, url
  };
  return axios.request({
    url: '/admin/permission/addOrUpdate',
    data,
    method: 'post'
  })
};

/**
 * 删除角色.
 * @param params 参数.
 */
export const deletePermissions = params => {

  const data = {
    ids: params
  };
  return axios.request({
    url: '/admin/permission/deletePermissions',
    data,
    method: 'post'
  })
};

/**
 * 获取权限树.
 * @param params 参数.
 */
export const queryPermissionTree = params => {

  return axios.request({
    url: '/admin/permission/queryPermissionTree?roleId=' + params.roleId,
    method: 'get'
  })
};
