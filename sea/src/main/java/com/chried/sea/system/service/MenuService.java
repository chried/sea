package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.MenuDTO;
import com.chried.sea.system.model.entity.MenuEntity;

import java.util.Collection;
import java.util.List;

/**
 * 菜单服务.
 *
 * @author chried
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 添加/更新.
     *
     * @param menu 菜单.
     * @return 信息.
     */
    Result<MenuEntity> addOrUpdate(MenuDTO menu);

    /**
     * 单个删除菜单.
     *
     * @param id 删除.
     * @return 信息.
     */
    Result<String> delete(String id);

    /**
     * 批量删除菜单.
     *
     * @param ids id集合.
     * @return 信息.
     */
    Result<String> delete(Collection<String> ids);

    /**
     * 查询子菜单.
     *
     * @param parentId 父节点.
     * @return 子菜单信息.
     */
    List<MenuEntity> queryByParentId(String parentId);

    /**
     * 查询菜单树.
     * <pre>
     *     关联角色那么被选中.
     * </pre>
     *
     * @param roleId 角色id.
     * @return 菜单树.
     */
    List<TreeNode> queryMenuTree(String roleId);
}
