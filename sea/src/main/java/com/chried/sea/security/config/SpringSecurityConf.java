package com.chried.sea.security.config;

import com.chried.sea.security.filter.JwtAuthenticationTokenFilter;
import com.chried.sea.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.chried.sea.security.handle.*;
import com.chried.sea.security.interceptor.MyFilterSecurityInterceptor;
import com.chried.sea.security.util.SkipPathRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * security配置.
 *
 * @author chried
 */
@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    @Resource
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    AjaxAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    AjaxLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    AjaxAccessDeniedHandler accessDeniedHandler;

    @Resource
    SelfUserDetailsService userDetailsService;

    @Resource
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 加入自定义的安全认证
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 去掉 CSRF
        http.csrf().disable()
                .cors().and()
                // 使用 JWT，关闭token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**")
                // RBAC 动态 url 认证
                .access("@rbacAuthorityService.hasPermission(request,authentication)")
                .anyRequest().permitAll()
                .and()
                //开启登录
                .formLogin()
                .loginProcessingUrl("/login")
                // 登录成功
                .successHandler(authenticationSuccessHandler)
                // 登录失败
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(300);
        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // JWT Filter
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //在 beforeFilter 之前添加 自定义 filter
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    /**
     * 注册jwt 认证
     *
     * @return token拦截器.
     * @throws Exception 异常.
     */
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        // 不需要token 验证的url
        List<String> pathsToSkip = Arrays.asList("/demo/login/", "/demo/index", "/static");
        //　需要验证token　的url.
        String processingPath = "/auth/**";
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
        return new JwtAuthenticationTokenFilter(authenticationManager(), matcher);
    }

    /**
     * 验证登录验证码
     *
     * @return filter.
     * @throws Exception 异常.
     */
    @Bean
    public UsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        return new MyUsernamePasswordAuthenticationFilter(authenticationManagerBean(), authenticationSuccessHandler, authenticationFailureHandler);
    }
}
