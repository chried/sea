package com.chried.core.enums;

/**
 * 返回结构枚举定义.
 *
 * <pre>
 *     主要约束一些常用返回信息以及编号.
 * </pre>
 *
 * @author chried
 */
public enum EnumResult {

    /**
     * 系统成功.
     */
    SUCCESS(200, "操作成功"),
    /**
     * 系统错误.
     */
    SYSTEM_ERROR(801, "系统错误"),

    /**
     * token过期.
     */
    TOKEN_EXPIRE(1101, "token过期"),

    /**
     * token无效.
     */
    TOKEN_INVALID(1102, "token无效"),

    /**
     * 无权访问.
     */
    Unauthorized(403, "无权访问"),

    /**
     * 账号或者密码错误.
     */
    ACCOUNT_OR_PASSWORD_ERROR(601, "账号或者密码错误"),

    /**
     * 账号锁定.
     */
    ACCOUNT_LOCK(602, "账号锁定"),

    /**
     * 登录失败.
     */
    LOGIN_ERROR(603, "登录失败"),

    /**
     * 未登录.
     */
    NOT_LOGIN(604, "未登录，请登录"),
    ;

    /**
     * 编号.
     */
    private int code;

    /**
     * 信息.
     */
    private String msg;

    EnumResult(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
