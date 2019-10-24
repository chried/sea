package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 验证错误实体类.
 *
 * @author chried
 */
@ApiModel(description = "验证错误实体类")
public class ValidationError implements Serializable {

    /**
     * 属性.
     */
    @ApiModelProperty(name = "field", value = "属性")
    private String field;

    /**
     * 报错消息.
     */
    @ApiModelProperty(name = "msg", value = "报错消息")
    private String msg;

    /**
     * 无参构造器.
     */
    public ValidationError() {
    }

    /**
     * 构造器.
     *
     * @param field 属性.
     * @param msg   错误消息.
     */
    public ValidationError(String field, String msg) {
        this.field = field;
        this.msg = msg;
    }

    /**
     * Gets the value of field.
     *
     * @return the value of field.
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the field.
     * <p>You can use getField() to get the value of field.</p>
     *
     * @param field field.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets the value of msg.
     *
     * @return the value of msg.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the msg.
     * <p>You can use getMsg() to get the value of msg.</p>
     *
     * @param msg msg.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
