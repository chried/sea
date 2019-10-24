package com.chried.sea.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.exception.SystemException;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.mapper.MenuMapper;
import com.chried.sea.system.model.dto.MenuDTO;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.model.entity.RoleMenuEntity;
import com.chried.sea.system.model.vo.MenuResult;
import com.chried.sea.system.utils.MenuConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 添加.
     *
     * @param menu 菜单.
     * @return 信息.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<MenuEntity> addOrUpdate(MenuDTO menu) {
        log.info("添加菜单...");

        MenuEntity menuEntity = menuMapper.findByNameOrCode(menu.getName(), menu.getCode());
        if (menuEntity != null) {
            if (menu.getId() == null) {

                throw new ValidationException("菜单名称编号必须唯一");
            }
            if (!menuEntity.getId().equals(menu.getId())) {

                throw new ValidationException("菜单名称编号必须唯一");
            }
        }

        if (menuEntity == null) {
            menuEntity = new MenuEntity();
        }
        if (StringUtils.isNotBlank(menu.getId())) {
            menuEntity.setId(menu.getId());
        }
        menuEntity.setCode(menu.getCode());
        menuEntity.setName(menu.getName());
        menuEntity.setIcon(menu.getIcon());

        if (StringUtils.isNotBlank(menu.getParentId())) {
            menuEntity.setParentId(menu.getParentId());
        }
        menuEntity.setUrl(menu.getUrl());

        this.saveOrUpdate(menuEntity);

        return Result.of(menuEntity);
    }

    /**
     * 单个删除菜单.
     *
     * @param id 删除.
     * @return 信息.
     */
    @Override
    public Result<String> delete(String id) {
        log.info("删除菜单...");

        if (!isDelete(Collections.singletonList(id))) {

            throw new SystemException("菜单关联角色");
        }

        this.removeById(id);
        return Result.of("删除成功");
    }

    /**
     * 批量删除菜单.
     *
     * @param ids id集合.
     * @return 信息.
     */
    @Override
    public Result<String> delete(Collection<String> ids) {
        log.info("批量删除菜单...");

        if (!isDelete(ids)) {

            throw new SystemException("菜单关联角色");
        }

        this.removeByIds(ids);
        return Result.of("批量删除成功");
    }

    /**
     * 查询是否可以删除，关联role_menu如果有数据就不能删除.
     *
     * @param ids 主键ids.
     * @return 是否可以删除.
     */
    private boolean isDelete(Collection<String> ids) {

        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("id", ids);

        List<RoleMenuEntity> roleMenuEntities = roleMenuService.list(queryWrapper);

        return CollectionUtils.isEmpty(roleMenuEntities);
    }

    /**
     * 查询子菜单.
     *
     * @param parentId 父节点.
     * @return 子菜单信息.
     */
    @Override
    public List<MenuEntity> queryByParentId(String parentId) {
        log.info("查询子菜单...parentId:[{}]", parentId);

        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);

        return this.list(queryWrapper);
    }

    /**
     * 查询菜单树.
     * <pre>
     *     关联角色那么被选中.
     * </pre>
     *
     * @param roleId 角色id.
     * @return 菜单树.
     */
    @Override
    public List<TreeNode> queryMenuTree(String roleId) {
        log.info("查询菜单树...");
        // 查询所有的角色.
        List<MenuEntity> menuEntities = this.list();
        Map<Optional<String>, List<MenuEntity>> parentMap = menuEntities.stream().collect(Collectors.groupingBy(s -> Optional.ofNullable(s.getParentId())));

        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);

        List<RoleMenuEntity> roleMenuEntities = roleMenuService.list(queryWrapper);
        // 筛选出已经配置好的menuId.
        List<String> menuIds = roleMenuEntities.stream().map(s -> s.getMenuId()).collect(Collectors.toList());


        List<TreeNode> resultTreeNodes = new ArrayList<>();
        parentMap.forEach((k, v) -> {

            if (!k.isPresent()) {

                for (MenuEntity menuEntity : v) {
                    if (parentMap.get(Optional.of(menuEntity.getId())) == null) {
                        TreeNode treeNode = new TreeNode();
                        if (menuIds.contains(menuEntity.getId())) {
                            treeNode.setChecked(true);
                        }
                        treeNode.setTitle(menuEntity.getName());
                        treeNode.setCode(menuEntity.getName());
                        treeNode.setId(menuEntity.getId());
                        resultTreeNodes.add(treeNode);
                    }
                }
            } else {

                Optional<MenuEntity> menuEntityOptional = parentMap.get(Optional.empty()).stream().filter(s -> s.getId().equals(k.get())).findFirst();
                if (menuEntityOptional.isPresent()) {
                    MenuEntity menuEntity = menuEntityOptional.get();

                    TreeNode parentTreeNode = new TreeNode();

                    parentTreeNode.setTitle(menuEntity.getName());
                    parentTreeNode.setCode(menuEntity.getName());
                    parentTreeNode.setId(menuEntity.getId());
                    parentTreeNode.setExpand(true);

                    List<TreeNode> childTreeNode = v.stream().map(s -> {
                        TreeNode treeNode = new TreeNode();
                        if (menuIds.contains(s.getId())) {
                            treeNode.setChecked(true);
                        }
                        treeNode.setTitle(s.getName());
                        treeNode.setCode(s.getName());
                        treeNode.setId(s.getId());
                        return treeNode;
                    }).collect(Collectors.toList());
                    parentTreeNode.setChildren(childTreeNode);
                    resultTreeNodes.add(parentTreeNode);
                }
            }

        });
        return resultTreeNodes;
    }
}
