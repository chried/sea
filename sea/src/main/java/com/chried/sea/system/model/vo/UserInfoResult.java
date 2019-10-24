package com.chried.sea.system.model.vo;

import com.chried.core.param.Refer;
import com.chried.sea.system.model.entity.MenuEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息返回类.
 *
 * @author chried
 */
@ApiModel(description = "用户返回信息")
@Data
public class UserInfoResult implements Serializable {

    /**
     * 用户id.
     */
    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;

    /**
     * 账号.
     */
    @ApiModelProperty(name = "account", value = "账号")
    private String account;

    /**
     * 昵称.
     */
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

    /**
     * 头像.
     */
    @ApiModelProperty(name = "portrait", value = "头像")
    private String portrait;

    /**
     * 电子邮箱.
     */
    @ApiModelProperty(name = "email", value = "电子邮箱")
    private String email;

    /**
     * 角色集.
     */
    @ApiModelProperty(name = "roles", value = "角色集")
    private List<Refer> roles;

    /**
     * 菜单集.
     */
    @ApiModelProperty(name = "menus", value = "菜单集")
    private List<MenuResult> menus;

    /**
     * 权限集.
     */
    @ApiModelProperty(name = "permissions", value = "权限集")
    private List<Refer> permissions;

}
