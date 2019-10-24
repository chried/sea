package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色关联权限.
 *
 * @author chried
 */
@ApiModel(description = "角色关联权限")
@TableName(value = "role_permission")
@Setter
@Getter
public class RolePermissionEntity extends BaseEntity<RolePermissionEntity> {

    /**
     * 角色ID.
     */
    @TableField(value = "role_id")
    @ApiModelProperty(name = "roleId", value = "角色ID")
    private String roleId;

    /**
     * 权限ID.
     */
    @TableField(value = "permission_id")
    @ApiModelProperty(name = "permissionId", value = "权限ID")
    private String permissionId;
}
