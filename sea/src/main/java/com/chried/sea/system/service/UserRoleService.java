package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.UserRoleEntity;

import java.util.List;

/**
 * 用户角色服务.
 *
 * @author chried
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    /**
     * 根据用户获取角色.
     *
     * @param userId 用户id.
     * @return 权限.
     */
    List<RoleEntity> findRoleByUserId(String userId);
}
