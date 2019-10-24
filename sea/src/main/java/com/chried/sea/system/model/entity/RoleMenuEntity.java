package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色菜单表.
 *
 * @author chried
 */
@Setter
@Getter
@ApiModel(description = "角色菜单表")
@TableName(value = "role_menu")
public class RoleMenuEntity extends BaseEntity<RoleMenuEntity> {

    /**
     * 角色ID.
     */
    @ApiModelProperty(name = "roleId", value = "角色ID")
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 菜单ID.
     */
    @ApiModelProperty(name = "menuId", value = "菜单ID")
    @TableField(value = "menu_id")
    private String menuId;
}
