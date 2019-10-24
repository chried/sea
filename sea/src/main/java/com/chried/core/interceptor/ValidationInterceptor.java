/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chried.core.interceptor;

import com.chried.core.enums.EnumResult;
import com.chried.core.exception.TokenException;
import com.chried.core.param.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 验证拦截器.
 *
 * @author gwb
 * @date 2019-05-09
 * @since 1.0-SNAPSHOT
 */
@Aspect
@Component
@Slf4j
public class ValidationInterceptor {

    /**
     * 验证拦截切面.
     * <pre>
     * execution 指拦截哪些运行方法，编写格式为
     * 修饰符 返回类型 包.包.包.方法(参数)
     * .. 表示“所有(包、参数)”的意思
     * </pre>
     *
     * @param pjp           切入点
     * @param bindingResult 拦截变量
     * @return 对象.s
     * @throws Throwable 异常
     */
    @Around("execution(* com.chried.sea.controller..*(..)) && args(.., bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult)
            throws Throwable {

        if (bindingResult.hasErrors()) {

            throw new BindException(bindingResult);
        }

        return pjp.proceed();
    }

    /**
     * 验证token是否有效.
     *
     * @param pjp         切入点.
     * @param accessToken 拦截变量.
     * @return 对象.
     * @throws Throwable 异常.
     */
    @Around("execution(* com.chried.sea.controller..*(..)) && args(accessToken,..)")
    public Object doAroundToken(ProceedingJoinPoint pjp, @RequestHeader(value = Parameter.ACCESS_TOKEN) String accessToken)
            throws Throwable {

        if (!accessToken.startsWith(Parameter.JWT_TOKEN_HEADER_PREFIX)) {

            throw new TokenException(EnumResult.TOKEN_INVALID);
        }

        return pjp.proceed();
    }

}
