package com.chried.core.exception;

import com.chried.core.enums.EnumResult;

/**
 * 定义系统错误.
 * <pre>
 *     用来约束捕获系统中的异常错误，异常统一拦截，在处理成用户友好提示.
 *      主要用于，系统内出现的异常抛出.
 * </pre>
 */
public class SystemException extends RuntimeException {

    /**
     * 编号.
     */
    private int code;

    /**
     * 错误信息.
     */
    private String msg;

    /**
     * 信息构造器.
     *
     * @param msg 信息.
     */
    public SystemException(String msg) {

        super(msg);
        this.code = EnumResult.SYSTEM_ERROR.getCode();
        this.msg = msg;
    }

    public SystemException(EnumResult result) {

        this(result.getMsg());
        this.code = result.getCode();
    }

    public SystemException(int code, String msg) {

        this(msg);
        this.code = code;
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
