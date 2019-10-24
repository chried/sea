import axios from '@/libs/api.request'

/**
 * 获取列表数据.
 * @param params 参数.
 */
export const getTableData = ({q, size, current}) => {

  const data = {
    q, size, current,
    columns: ['msg'],
  };
  return axios.request({
    url: '/url/error/query',
    data,
    method: 'post'
  })
};
