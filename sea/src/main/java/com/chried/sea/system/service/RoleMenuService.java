package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.model.entity.RoleMenuEntity;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单服务.
 *
 * @author chried
 */
public interface RoleMenuService extends IService<RoleMenuEntity> {

    /**
     * 根据角色获取菜单.
     *
     * @param roleId 角色id.
     * @return 菜单列表.
     */
    List<MenuEntity> queryByRoleId(String roleId);

    /**
     * 根据角色ids获取菜单.
     *
     * @param roleIds 角色ids.
     * @return 菜单列表.
     */
    List<MenuEntity> queryByRoleIds(Collection<String> roleIds);
}
