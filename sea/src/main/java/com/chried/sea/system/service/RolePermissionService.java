package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 角色权限服务.
 *
 * @author chried
 */
public interface RolePermissionService extends IService<RolePermissionEntity> {

    /**
     * 根据角色ID获取权限.
     *
     * @param roleId 角色id.
     * @return 权限列表.
     */
    List<PermissionEntity> selectPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色ID获取权限.
     *
     * @param roleIds 角色ids.
     * @return 权限列表.
     */
    List<PermissionEntity> selectPermissionByRoleIds(@Param("roleIds") Collection<String> roleIds);
}
