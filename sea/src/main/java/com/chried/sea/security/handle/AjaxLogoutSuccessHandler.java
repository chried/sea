package com.chried.sea.security.handle;

import com.alibaba.fastjson.JSON;
import com.chried.core.enums.EnumResult;
import com.chried.core.param.Result;
import com.chried.utils.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录调用.
 *
 * @author chried
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Result<Object> of = Result.of(EnumResult.SUCCESS);
        of.setMsg("退出成功");

        ResultUtil.out(httpServletResponse, of);
    }
}
