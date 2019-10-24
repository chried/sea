package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表.
 */
@TableName(value = "role")
@Setter
@Getter
public class RoleEntity extends BaseEntity<RoleEntity> {

    /**
     * 名称.
     */
    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    /**
     * 标志.
     */
    @ApiModelProperty(name = "sign", value = "标志")
    private String sign;
}
