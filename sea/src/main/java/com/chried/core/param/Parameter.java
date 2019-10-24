package com.chried.core.param;

/**
 * 全局参数配置.
 *
 * @author chried
 */
public final class Parameter {

    /**
     * 定义版本号.
     * <pre>
     *     默认版本号.
     * </pre>
     */
    public static final String VERSION = "1.0";

    /**
     * JWT请求token.
     */
    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 设置jwt生成token前缀.
     * <pre>
     *     可用于一些过滤错误token.
     * </pre>
     */
    public static final String JWT_TOKEN_HEADER_PREFIX = "SEA+";

    /**
     * 降序.
     */
    public static final String DESC = "desc";

    /**
     * 升序.
     */
    public static final String ASC = "asc";
}
