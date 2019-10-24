package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.sea.system.mapper.RoleMenuMapper;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.model.entity.RoleMenuEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色菜单服务实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuEntity> implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据角色获取菜单.
     *
     * @param roleId 角色id.
     * @return 菜单列表.
     */
    @Override
    public List<MenuEntity> queryByRoleId(String roleId) {
        log.info("根据角色获取权限,roleId:{}", roleId);

        if (roleId == null) {

            return new ArrayList<>();
        }
        return roleMenuMapper.queryByRoleId(roleId);
    }

    /**
     * 根据角色ids获取菜单.
     *
     * @param roleIds 角色ids.
     * @return 菜单列表.
     */
    @Override
    public List<MenuEntity> queryByRoleIds(Collection<String> roleIds) {
        log.info("根据角色获取权限,roleIds:{}", roleIds);

        if (CollectionUtils.isEmpty(roleIds)) {

            return new ArrayList<>();
        }

        return roleMenuMapper.queryByRoleIds(roleIds);
    }


}
