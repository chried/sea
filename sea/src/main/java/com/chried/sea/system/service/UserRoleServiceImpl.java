package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.exception.SystemException;
import com.chried.sea.system.mapper.UserRoleMapper;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色服务实现.
 */
@Service
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户获取角色.
     *
     * @param userId 用户id.
     * @return 权限.
     */
    @Override
    public List<RoleEntity> findRoleByUserId(String userId) {
        log.info("根据用户获取角色...");

        if (userId == null) {

            throw new SystemException("根据用户获取角色,userId不能为空");
        }

        return userRoleMapper.selectRoleByUserId(userId);
    }
}
