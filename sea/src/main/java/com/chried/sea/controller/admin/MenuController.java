package com.chried.sea.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.core.param.IdsForm;
import com.chried.core.param.PageParameter;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.MenuDTO;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器.
 *
 * @author chried
 */
@RequestMapping(value = "/admin/menu")
@RestController("admin$menu")
@Api(tags = "菜单控制器")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 添加/编辑菜单.
     *
     * @param dto 菜单.
     * @return 添加信息.
     */
    @ApiOperation(value = "添加/编辑菜单")
    @PostMapping(value = "addOrUpdate")
    public Result<MenuEntity> addOrUpdate(@RequestBody MenuDTO dto) {

        return menuService.addOrUpdate(dto);
    }

    /**
     * 分页查询.
     *
     * @return 分页.
     */
    @PostMapping(value = "query")
    @ApiOperation(value = "分页查询")
    public Page<MenuEntity> query(@RequestBody PageParameter<MenuEntity> page) {

        return (Page<MenuEntity>) menuService.page(page.createPage(), page.createWrapper());
    }

    /**
     * 删除菜单.
     *
     * @param form ids.
     * @return 删除信息.
     */
    @PostMapping(value = "deleteMenus")
    @ApiOperation(value = "删除菜单")
    public Result<String> deleteMenus(@RequestBody IdsForm form) {

        return menuService.delete(form.getIds());
    }

    /**
     * 获取子菜单.
     *
     * @param parentId 父级id.
     * @return 删除信息.
     */
    @GetMapping(value = "queryChild")
    @ApiOperation(value = "删除菜单")
    public Result<List<MenuEntity>> queryChild(@ApiParam(name = "parentId", value = "父级ID") @RequestParam(value = "parentId") String parentId) {

        return Result.of(menuService.queryByParentId(parentId));
    }

    /**
     * 获取菜单树.
     *
     * @param roleId 角色id.
     * @return 菜单树.
     */
    @GetMapping(value = "queryMenuTree")
    @ApiOperation(value = "获取菜单树")
    public Result<List<TreeNode>> queryMenuTree(@ApiParam(name = "roleId", value = "角色id") @RequestParam(value = "roleId") String roleId) {

        return Result.of(menuService.queryMenuTree(roleId));
    }
}
