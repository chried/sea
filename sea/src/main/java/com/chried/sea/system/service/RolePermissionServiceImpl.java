package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.exception.SystemException;
import com.chried.sea.system.mapper.RolePermissionMapper;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * 角色权限服务实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 根据角色ID获取权限.
     *
     * @param roleId 角色id.
     * @return 权限列表.
     */
    @Override
    public List<PermissionEntity> selectPermissionByRoleId(String roleId) {
        log.info("根据角色查询权限,roleId={}", roleId);

        if (StringUtils.isBlank(roleId)) {

            throw new SystemException("角色查询权限，roleId不能为空");
        }

        return rolePermissionMapper.selectPermissionByRoleId(roleId);
    }

    /**
     * 根据角色ID获取权限.
     *
     * @param roleIds 角色ids.
     * @return 权限列表.
     */
    @Override
    public List<PermissionEntity> selectPermissionByRoleIds(Collection<String> roleIds) {
        log.info("根据角色ids查询权限,roleIds={}", roleIds);

        if (CollectionUtils.isEmpty(roleIds)) {

            throw new SystemException("角色IDS查询权限,roleIds不能为空");
        }

        return rolePermissionMapper.selectPermissionByRoleIds(roleIds);
    }
}
