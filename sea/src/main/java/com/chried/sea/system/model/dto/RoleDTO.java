package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色传输类.
 *
 * @author chried
 */
@ApiModel(description = "角色传输类")
@Data
public class RoleDTO implements Serializable {

    /**
     * 主键id.
     */
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    /**
     * 名称.
     */
    @ApiModelProperty(name = "name", value = "名称")
    @NotNull(message = "角色名称不能为空")
    private String name;

    /**
     * 标志.
     */
    @ApiModelProperty(name = "sign", value = "标志")
    @NotNull(message = "角色标志不能为空")
    private String sign;
}
