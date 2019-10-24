package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户权限中间表.
 *
 * @author chried
 */
@ApiModel(description = "用户权限表")
@TableName(value = "user_role")
@Setter
@Getter
public class UserRoleEntity extends BaseEntity<UserRoleEntity> {

    /**
     * 用户ID.
     */
    @ApiModelProperty(name = "userId", value = "用户ID")
    @TableField(value = "user_id")
    private String userId;

    /**
     * 角色ID.
     */
    @ApiModelProperty(name = "roleId", value = "角色ID")
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 过期时间.
     */
    @ApiModelProperty(name = "expireDate", value = "过期时间")
    @TableField(value = "expire_date")
    private LocalDateTime expireDate;
}
