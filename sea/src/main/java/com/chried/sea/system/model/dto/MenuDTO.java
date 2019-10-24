package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单传输类.
 *
 * @author chried
 */
@ApiModel(description = "菜单传输类")
@Data
public class MenuDTO implements Serializable {

    /**
     * 主键id.
     */
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    /**
     * 菜单名称.
     */
    @ApiModelProperty(name = "name", value = "菜单名称")
    @NotNull(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单编号.
     */
    @ApiModelProperty(name = "code", value = "菜单编号")
    @NotNull(message = "菜单编号不能为空")
    private String code;

    /**
     * 父节点.
     */
    @ApiModelProperty(name = "parentId", value = "父节点")
    private String parentId;

    /**
     * url.
     */
    @ApiModelProperty(name = "url", value = "url")
    @NotNull(message = "菜单url不能为空")
    private String url;

    /**
     * 图标.
     */
    @NotNull(message = "图标不能为空")
    @ApiModelProperty(name = "icon", value = "图标")
    private String icon;
}
