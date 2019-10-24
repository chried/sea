/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chried.core.resolver;

import com.chried.core.enums.EnumResult;
import com.chried.core.exception.MyAccessDeniedException;
import com.chried.core.exception.SystemException;
import com.chried.core.exception.TokenException;
import com.chried.core.param.Result;
import com.chried.core.param.ValidationError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一异常处理.
 *
 * @author chried
 */
@RestControllerAdvice(basePackages = "com.chried")
@Slf4j
public class ExceptionResolver {

    public ExceptionResolver() {
    }

    /**
     * 系统异常.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processException(Exception ex) {

        log.error("出现了不可知的运行错误!", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);

        result.setData(ex.getMessage());

        return result;
    }

    /**
     * 全局系统异常.
     * <pre>
     *     转出用户友好提示.
     * </pre>
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {SystemException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processSystemException(SystemException ex) {

        log.error("出现逻辑异常,{}!", ExceptionUtils.getStackTrace(ex));

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);

        result.setData(ex.getMessage());
        result.setMsg(ex.getMessage());

        return result;
    }

    /**
     * token过期异常.
     *
     * @param ex token过期异常.
     * @return 提示信息.
     */
    @ExceptionHandler(value = {TokenException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processSystemException(TokenException ex) {

        log.error("验证token出现异常,{}!", ExceptionUtils.getStackTrace(ex));

        return Result.of(ex.getCode(), ex.getMsg());
    }

    /**
     * 权限判断.
     *
     * @param ex 权限异常.
     * @return 提示信息.
     */
    @ExceptionHandler(value = {MyAccessDeniedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processSystemException(MyAccessDeniedException ex) {

        log.error("权限不够,{}!", ExceptionUtils.getStackTrace(ex));

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 权限判断.
     *
     * @param ex 权限异常.
     * @return 提示信息.
     */
    @ExceptionHandler(value = {InsufficientAuthenticationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processSystemException(InsufficientAuthenticationException ex) {

        log.error("权限不够11,{}!", ExceptionUtils.getStackTrace(ex));

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 自动注入的绑定错误.
     *
     * @param ex 异常.
     * @return 带有详细信息的错误提示.
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<List<ValidationError>> processBindException(BindException ex) {

        log.error("数据绑定错误", ex);

        List<ValidationError> errors = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        ex.getFieldErrors().forEach((fe) -> {
            errors.add(new ValidationError(fe.getField(), fe.getDefaultMessage()));
            sb.append(fe.getDefaultMessage());
            sb.append(",\n");
        });

        return Result.of(errors).fail(sb.toString());
    }

    /**
     * 捕获空指针异常.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Result<String> processNullPointerException(NullPointerException ex) {

        log.error("空指针异常", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 传入的参数类型不匹配.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        log.error("传入的参数类型不匹配", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 反序列化失败.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        log.error("反序列化失败", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 类型转换失败.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processInvalidFormatException(InvalidFormatException ex) {

        log.error("类型转换失败", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 全局拦截验证错误.
     *
     * @param ex 异常.
     * @return 消息.
     */
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processValidationException(ValidationException ex) {

        log.error(ex.getMessage(), ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }

    /**
     * 输入参数不匹配.
     *
     * @param ex 异常
     * @return 消息
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result<String> processIllegalArgumentException(IllegalArgumentException ex) {

        log.error("输入参数不匹配", ex);

        Result<String> result = Result.of(EnumResult.SYSTEM_ERROR);
        result.setData(ex.getMessage());
        return result;
    }
}
