package com.chried.sea.system.service;

import com.chried.core.param.Result;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.entity.UserEntity;
import com.chried.sea.system.model.vo.UserInfoResult;
import com.chried.service.AbstractEntityService;

/**
 * userService.
 *
 * @author chried
 */
public interface UserService extends AbstractEntityService<UserEntity> {

    /**
     * 根据token获取用户信息.
     *
     * @param token 令牌.
     * @return 用户信息.
     */
    UserInfoResult getUserInfo(String token);

    /**
     * 处理用户授权角色.
     *
     * @param userRoleDTO 用户授权角色传输类.
     * @return 返回信息.
     */
    Result<String> handleUserRole(HandleTreeDTO userRoleDTO);
}
