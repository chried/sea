package com.chried.core.exception;

import com.chried.core.enums.EnumResult;
import lombok.Data;

/**
 * 令牌过期异常.
 * <pre>
 *     跟前端约束异常值.
 * </pre>
 *
 * @author chried
 */
public class TokenException extends RuntimeException {

    /**
     * 错误编号.
     */
    private int code;

    /**
     * 错误消息.
     */
    private String msg;

    /**
     * 构造器.
     *
     * @param result 定义枚举.
     */
    public TokenException(EnumResult result) {
        super(result.getMsg());
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
