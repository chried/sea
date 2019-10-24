package com.chried.sea.security.interceptor;

import com.chried.sea.security.config.MyAccessDecisionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 权限验证过滤器，继承AbstractSecurityInterceptor、实现Filter是必须的
 * 首先，登陆后，每次访问资源都会被这个拦截器拦截，会执行doFilter这个方法，这个方法调用了invoke方法，其中fi断点显示是一个url
 * 最重要的是beforeInvocation这个方法，它首先会调用MyInvocationSecurityMetadataSource类的getAttributes方法获取被拦截url所需的权限
 * 在调用MyAccessDecisionManager类decide方法判断用户是否具有权限,执行完后就会执行下一个拦截器.
 *
 * @author chried
 */
@Component
@Slf4j
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Resource
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Resource
    private MyAccessDecisionManager myAccessDecisionManager;

    /**
     * 权限管理决断器
     */
    @Autowired
    public void setAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 登录后 每次请求都会调用这个拦截器进行请求过滤
     *
     * @param servletRequest  请求.
     * @param servletResponse 返回.
     * @param filterChain     处理.
     * @throws IOException      io异常.
     * @throws ServletException servlet异常.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    /**
     * 拦截请求处理
     *
     * @param fi 拦截.
     * @throws IOException      io异常.
     * @throws ServletException servlet异常.
     */
    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    /**
     * Completes the work of the <tt>AbstractSecurityInterceptor</tt> after the secure
     * object invocation has been completed.
     *
     * @param token          as returned by the {@link #beforeInvocation(Object)} method
     * @param returnedObject any object returned from the secure object invocation (may be
     *                       <tt>null</tt>)
     * @return the object the secure object invocation should ultimately return to its
     * caller (may be <tt>null</tt>)
     */
    @Override
    protected Object afterInvocation(InterceptorStatusToken token, Object returnedObject) {
        log.info("我是来刷的");
        return super.afterInvocation(token, returnedObject);
    }
}