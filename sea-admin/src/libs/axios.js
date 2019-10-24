import axios from 'axios'
import store from '@/store'
import {getToken, TOKEN_KEY} from '@/libs/util'
// import { Spin } from 'iview'
const addErrorLog = errorInfo => {
  const {statusText, status, request: {responseURL}} = errorInfo;
  let info = {
    errorType: 'ajax',
    errorCode: status,
    msg: statusText,
    url: responseURL
  };
  if (!responseURL.includes('save_error_logger')) store.dispatch('addErrorLog', info)
};

class HttpRequest {
  constructor(baseUrl = baseURL) {
    this.baseUrl = baseUrl;
    this.queue = {}
  }

  getInsideConfig() {
    return {
      baseURL: this.baseUrl,
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    }
  }

  destroy(url) {
    delete this.queue[url]
    if (!Object.keys(this.queue).length) {
      // Spin.hide()
    }
  }

  interceptors(instance, url) {
    // 请求拦截
    instance.interceptors.request.use(config => {
      // 添加全局的loading...
      if (!Object.keys(this.queue).length) {
        // Spin.show() // 不建议开启，因为界面不友好
      }
      const token = getToken();
      if (token) {
        config.headers[TOKEN_KEY] = token;
      }
      this.queue[url] = true;
      return config
    }, error => {
      return Promise.reject(error)
    })
    instance.interceptors.response.use(res => {
      this.destroy(url);
      const {data} = res;
      if (data.code === 1101) {
        return Promise.reject(res)
      }
      return {data, code: data.code}
    }, error => {
      this.destroy(url);
      let errorInfo = error.response;
      console.log(error);
      if (!errorInfo) {
        const {request: {statusText, status}, config} = JSON.parse(JSON.stringify(error));
        console.log(JSON.parse(JSON.stringify(error)));
        errorInfo = {
          statusText,
          status,
          request: {responseURL: config.url}
        }
      }
      addErrorLog(errorInfo);
      return Promise.reject(error)
    })
  }

  request(options) {
    const instance = axios.create();
    options = Object.assign(this.getInsideConfig(), options);
    this.interceptors(instance, options.url);
    return instance(options)
  }
}

export default HttpRequest
