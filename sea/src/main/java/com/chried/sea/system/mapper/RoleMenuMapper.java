package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.model.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单mapper.
 *
 * @author chried
 */
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

    /**
     * 根据角色获取菜单.
     *
     * @param roleId 角色id.
     * @return 菜单列表.
     */
    List<MenuEntity> queryByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色ids获取菜单.
     *
     * @param roleIds 角色ids.
     * @return 菜单列表.
     */
    List<MenuEntity> queryByRoleIds(@Param("roleIds") Collection<String> roleIds);
}
