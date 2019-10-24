package com.chried.sea.redis.cache;

import com.chried.sea.security.util.JwtTokenUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 用户缓存.
 * <pre>
 *     过期时间在jwt token基础上加200s.
 * </pre>
 *
 * @author chried
 */
@RedisHash(value = "system.UserCache", timeToLive = JwtTokenUtils.EXPIRATION_SECONDS + 200L)
@ApiModel(description = "用户缓存")
@Data
public class UserCache implements Serializable {

    /**
     * 主键id.
     * <pre>
     *     登录生成token.
     * </pre>
     */
    @Id
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    /**
     * 用户id.
     */
    @ApiModelProperty(name = "userId", value = "用户id")
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
     * 电话.
     */
    @ApiModelProperty(name = "phone", value = "电话")
    private String phone;

    /**
     * 电子邮箱.
     */
    @ApiModelProperty(name = "email", value = "电子邮箱")
    private String email;

    /**
     * 创建时间.
     */
    @ApiModelProperty(name = "createDate", value = "创建时间")
    private LocalDateTime createDate;

    /**
     * 权限.
     */
    @ApiModelProperty(name = "authorities", value = "权限集")
    private Collection<? extends GrantedAuthority> authorities;
}
