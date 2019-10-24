import axios from '@/libs/api.request'

/**
 * 获取次啊单列表.
 * @param q 关键字
 * @param size 每页展示.
 * @param current 当前页.
 * @returns {*}
 */
export const getTableData = ({q, size, current}) => {
  const data = {
    q, size, current,
    columns: ['name', 'code'],
    params: {
      'parent_id': null
    }
  };
  return axios.request({
    url: '/admin/menu/query',
    data,
    method: 'post'
  })
};

/**
 * 添加或者编辑菜单.
 * @param params 参数.
 */
export const addOrEditMenu = params => {
  const {id, parentId, name, code, url, icon} = params;

  const data = {
    id, parentId, name, code, url, icon
  };
  return axios.request({
    url: '/admin/menu/addOrUpdate',
    data,
    method: 'post'
  })
};

/**
 * 删除角色.
 * @param params 参数.
 */
export const deleteMenus = params => {

  const data = {
    ids: params
  };
  return axios.request({
    url: '/admin/menu/deleteMenus',
    data,
    method: 'post'
  })
};

/**
 * 获取子菜单数据.
 * @param params 参数.
 */
export const getChildTableData = params => {

  return axios.request({
    url: '/admin/menu/queryChild',
    params,
    method: 'get'
  })
};
/**
 * 获取菜单树.
 * @param params 参数.
 */
export const queryMenuTree = params => {

  return axios.request({
    url: '/admin/menu/queryMenuTree?roleId=' + params.roleId,
    method: 'get'
  })
};
