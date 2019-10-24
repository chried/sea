package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录实体类.
 *
 * @author chried
 */
@ApiModel(description = "登录传输类")
@Data
public class LoginDTO implements Serializable {

    /**
     * 账号.
     */
    @ApiModelProperty(name = "account", value = "账号")
    private String account;

    /**
     * 密码.
     */
    @ApiModelProperty(name = "password", value = "密码")
    private String password;

    /**
     * 验证码.
     */
    @ApiModelProperty(name = "code", value = "验证码")
    private String code;
}

