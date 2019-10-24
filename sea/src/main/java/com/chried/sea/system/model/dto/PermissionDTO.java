package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 权限传输类.
 *
 * @author chried
 */
@ApiModel(description = "权限传输类")
@Data
public class PermissionDTO implements Serializable {

    /**
     * 主键.
     */
    @ApiModelProperty(name = "id", value = "主键")
    private String id;

    /**
     * 名称.
     */
    @ApiModelProperty(name = "name", value = "名称")
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 标志.
     */
    @NotNull(message = "标志不能为空")
    @ApiModelProperty(name = "sign", value = "标志")
    private String sign;

    /**
     * 连接.
     */
    @ApiModelProperty(name = "url", value = "连接")
    @NotNull(message = "连接不能为空")
    private String url;
}
