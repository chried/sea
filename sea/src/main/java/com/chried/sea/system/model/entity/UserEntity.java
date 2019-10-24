package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户表.
 *
 * @author chried
 */
@ApiModel(description = "用户表")
@TableName(value = "user")
@Setter
@Getter
public class UserEntity extends AbstractEntity {

    /**
     * 账号.
     */
    @ApiModelProperty(name = "account", value = "账号")
    private String account;

    /**
     * 电话.
     */
    @ApiModelProperty(name = "phone", value = "电话")
    private String phone;

    /**
     * 密码.
     */
    @ApiModelProperty(name = "password", value = "密码")
    private String password;

    /**
     * 锁定时间.
     * <pre>
     *     如果大于当前时间，那么账号就是被锁定.
     * </pre>
     */
    @ApiModelProperty(name = "lockDate", value = "锁定时间")
    @TableField(value = "lock_date")
    private LocalDateTime lockDate;

    /**
     * 电子邮箱.
     */
    @ApiModelProperty(name = "email", value = "电子邮箱")
    private String email;

    /**
     * 头像.
     */
    @ApiModelProperty(name = "portrait", value = "头像")
    private String portrait;

    /**
     * 昵称.
     */
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

}
