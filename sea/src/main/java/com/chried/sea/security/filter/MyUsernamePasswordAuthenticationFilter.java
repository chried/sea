package com.chried.sea.security.filter;

import com.alibaba.fastjson.JSON;
import com.chried.sea.system.model.dto.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 自定义登录拦截器.
 *
 * @author chried
 */
@Slf4j
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public MyUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        //这句代码很重要，设置登陆的url 要和 WebSecurityConfig 配置类中的.loginProcessingUrl("/auth/v1/api/login/entry") 一致，
        // 如果不配置则无法执行 重写的attemptAuthentication 方法里面而是执行了父类UsernamePasswordAuthenticationFilter的attemptAuthentication（）
        this.setFilterProcessesUrl("/login");
        // AuthenticationManager 是必须的
        this.setAuthenticationManager(authenticationManager);
        //设置自定义登陆成功后的业务处理
        this.setAuthenticationSuccessHandler(successHandler);
        //设置自定义登陆失败后的业务处理
        this.setAuthenticationFailureHandler(failureHandler);
    }

    public MyUsernamePasswordAuthenticationFilter() {

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginDTO loginDTO = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                sb.append(inputStr);
            }
            loginDTO = JSON.parseObject(sb.toString(), LoginDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginDTO == null) {

            throw new UsernameNotFoundException("用户名或者密码错误");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginDTO.getAccount(), loginDTO.getPassword());

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        // 存储登录认证信息到上下文
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        // 记住我服onAuthenticationSuccess务
//        getRememberMeServices().loginSuccess(request, response, authResult);
//        // 触发事件监听器
//        if (this.eventPublisher != null) {
//            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
//        }
//        // 生成并返回token给客户端，后续访问携带此token
//        SecurityUserDetails securityUserDetails = (SecurityUserDetails) authResult.getPrincipal();
//        String token = JwtTokenUtil.generateAccessToken(securityUserDetails);
//        token = JwtTokenUtil.tokenPrefix + token;
//        ResultUtil.out(response, Result.of(token));
//    }
}
