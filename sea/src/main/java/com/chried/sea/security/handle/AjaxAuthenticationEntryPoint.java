package com.chried.sea.security.handle;

import com.alibaba.fastjson.JSON;
import com.chried.core.enums.EnumResult;
import com.chried.core.param.Result;
import com.chried.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问需要登录资源，未登录调用.
 *
 * @author chried
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ResultUtil.out(httpServletResponse, ResultUtil.resultMap(EnumResult.NOT_LOGIN));
    }
}
