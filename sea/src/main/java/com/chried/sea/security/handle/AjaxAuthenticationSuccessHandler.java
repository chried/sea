package com.chried.sea.security.handle;

import com.chried.core.param.Parameter;
import com.chried.core.param.Result;
import com.chried.sea.redis.cache.UserCache;
import com.chried.sea.redis.repository.UserCacheRepository;
import com.chried.sea.security.model.SelfUserDetails;
import com.chried.sea.security.util.JwtTokenUtils;
import com.chried.utils.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功调用.
 *
 * @author chried
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserCacheRepository userCacheRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();

        String jwtToken = JwtTokenUtils.generateToken(userDetails);

        Result<Object> of = Result.of(Parameter.JWT_TOKEN_HEADER_PREFIX + jwtToken);

        UserCache userCache = userDetails.toUserCache();

        userCache.setId(jwtToken);

        userCacheRepository.save(userCache);

        ResultUtil.out(httpServletResponse, of);
    }
}