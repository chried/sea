package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限实体类.
 *
 * @author chried
 */
@ApiModel(description = "权限实体类")
@TableName(value = "permission")
@Setter
@Getter
public class PermissionEntity extends BaseEntity<PermissionEntity> {

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

    /**
     * 连接.
     */
    @ApiModelProperty(name = "url", value = "连接")
    private String url;
}
