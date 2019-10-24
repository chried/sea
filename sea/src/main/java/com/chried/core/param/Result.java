package com.chried.core.param;

import com.chried.core.enums.EnumResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义返回结构.
 *
 * @param <T> 泛型.
 * @author chried
 */
@ApiModel(description = "接口返回结构")
@Data
public class Result<T> implements Serializable {

    /**
     * 编号.
     */
    @ApiModelProperty(name = "code", value = "编号")
    private int code;

    /**
     * 返回信息.
     */
    @ApiModelProperty(name = "msg", value = "返回信息")
    private String msg;

    /**
     * 返回数据.
     */
    @ApiModelProperty(name = "data", value = "返回数据")
    private T data;

    /**
     * 无参构造器.
     */
    private Result() {

        this.code = EnumResult.SUCCESS.getCode();
        this.msg = EnumResult.SUCCESS.getMsg();
    }

    /**
     * 构造器.
     *
     * @param code 编号.
     * @param msg  信息.
     */
    private Result(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造器.
     *
     * @param data 数据.
     */
    private Result(T data) {

        this();
        this.data = data;
    }

    /**
     * 初始化result.
     * <pre>
     *     建议采用链式调用，不建议构造函数初始化该类.
     * </pre>
     *
     * @param data 数据.
     * @param <TD> 泛型.
     * @return result实例.
     */
    public static <TD> Result<TD> of(TD data) {

        return new Result<>(data);
    }

    /**
     * 初始化result.
     *
     * @param code 编号.
     * @param msg  信息.
     * @param <TD> 泛型.
     * @return result实例.
     */
    public static <TD> Result<TD> of(int code, String msg) {

        return new Result<>(code, msg);
    }

    /**
     * 初始化result.
     *
     * @param result 枚举.
     * @param <TD>   泛型.
     * @return result实例.
     */
    public static <TD> Result<TD> of(EnumResult result) {

        return new Result<>(result.getCode(), result.getMsg());
    }

    /**
     * 设置操作信息.
     *
     * @param msg 信息.
     * @return result实例.
     */
    public Result<T> msg(String msg) {

        this.msg = msg;

        return this;
    }

    /**
     * 设置操作信息.
     * <pre>
     *     用于跟前端约定.
     * </pre>
     *
     * @param code 编号.
     * @param msg  信息.
     * @return result实例.
     */
    public Result<T> msg(int code, String msg) {

        this.msg = msg;
        this.code = code;

        return this;
    }

    /**
     * 设置错误信息.
     *
     * @param msg 错误信息.
     * @return result实例.
     */
    public Result<T> fail(String msg) {

        this.msg = msg;
        this.code = EnumResult.SYSTEM_ERROR.getCode();

        return this;
    }

    /**
     * 设置错误信息》
     * <pre>
     *     用于跟前端约定.
     * </pre>
     *
     * @param code 错误码.
     * @param msg  信息.
     * @return result实例.
     */
    public Result<T> fail(int code, String msg) {

        this.code = code;
        this.msg = msg;

        return this;
    }

}
