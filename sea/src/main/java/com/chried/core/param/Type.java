package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简化类.
 * <pre>
 *     用户传输code,name，不用传输整个对象.
 * </pre>
 *
 * @author chried
 */
@ApiModel(description = "简化类")
@Data
public class Type implements Serializable {

    /**
     * 编号.
     */
    @ApiModelProperty(name = "code", value = "编号")
    private String code;

    /**
     * 名称.
     */
    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    public Type() {

    }

    public Type(String code, String name) {

        this.code = code;
        this.name = name;
    }
}
