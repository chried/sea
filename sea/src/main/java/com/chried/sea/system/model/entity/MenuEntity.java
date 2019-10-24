package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单.
 *
 * @author chried
 */
@ApiModel(description = "菜单表")
@TableName(value = "menu")
@Setter
@Getter
public class MenuEntity extends BaseEntity<MenuEntity> {

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
     * 父节点.
     */
    @ApiModelProperty(name = "parentId", value = "父节点")
    @TableField(value = "parent_id")
    private String parentId;

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

}
