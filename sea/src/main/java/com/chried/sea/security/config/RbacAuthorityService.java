package com.chried.sea.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证url.
 *
 * @author chried
 */
@Component("rbacAuthorityService")
public class RbacAuthorityService {

    /**
     * 权限验证.
     *
     * @param request        请求.
     * @param authentication 当前登录信息.
     * @return 布尔值.
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        return userInfo instanceof UserDetails;
    }
}