package com.chried.sea.security.handle;

import com.alibaba.fastjson.JSON;
import com.chried.core.enums.EnumResult;
import com.chried.core.param.Result;
import com.chried.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败调用.
 *
 * @author chried
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ResultUtil.out(httpServletResponse, ResultUtil.resultMap(EnumResult.LOGIN_ERROR));
    }
}