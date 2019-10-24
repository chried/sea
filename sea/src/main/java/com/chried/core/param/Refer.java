package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简化实体类.
 * <pre>
 *     简化实体类.传说id,name,code.
 * </pre>
 *
 * @author chried
 */
@Data
@ApiModel(description = "简化实体类")
public class Refer implements Serializable {

    /**
     * 主键ID.
     */
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

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

    public Refer() {

    }

    public Refer(String id, String code, String name) {

        this.id = id;
        this.code = code;
        this.name = name;
    }

    /**
     * 转换type.
     *
     * @param code 编号.
     * @param name 名称.
     * @return type.
     */
    public static Type toType(String code, String name) {

        return new Type(code, name);
    }

    /**
     * 转换type.
     *
     * @return type.
     */
    public Type toType() {

        return new Type(this.getCode(), this.getName());
    }
}
