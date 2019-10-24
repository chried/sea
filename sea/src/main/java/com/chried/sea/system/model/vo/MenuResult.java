package com.chried.sea.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单返回类.
 *
 * @author chried
 */
@Data
@ApiModel(description = "菜单返回类")
public class MenuResult implements Serializable {

    /**
     * 主键id.
     */
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    /**
     * 菜单名字.
     */
    @ApiModelProperty(name = "name", value = "菜单名字")
    private String name;

    /**
     * 菜单编号.
     */
    @ApiModelProperty(name = "code", value = "菜单编号")
    private String code;

    /**
     * 连接.
     */
    @ApiModelProperty(name = "url", value = "连接")
    private String url;

    /**
     * 图标.
     */
    @ApiModelProperty(name = "icon", value = "图标")
    private String icon;

    /**
     * 子菜单.
     */
    @ApiModelProperty(name = "children", value = "子菜单")
    private List<MenuResult> children;
}
