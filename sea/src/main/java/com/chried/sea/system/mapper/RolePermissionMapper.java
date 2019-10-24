package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色权限mapper.
 *
 * @author chried
 */
@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {

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
