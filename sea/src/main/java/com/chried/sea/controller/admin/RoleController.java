package com.chried.sea.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.core.param.IdsForm;
import com.chried.core.param.PageParameter;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.dto.RoleDTO;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色控制器.
 *
 * @author chried
 */
@RestController("admin$role")
@RequestMapping(value = "admin/role")
@Api(tags = "角色控制器")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 分页查询.
     *
     * @return 分页.
     */
    @PostMapping(value = "query")
    @ApiOperation(value = "分页查询")
    public Page<RoleEntity> query(@RequestBody PageParameter<RoleEntity> page) {

        return (Page<RoleEntity>) roleService.page(page.createPage(), page.createWrapper());
    }

    /**
     * 添加/编辑.
     *
     * @param roleDTO 传输类.
     * @param result  验证.
     * @return 角色.
     */
    @ApiOperation(value = "添加/编辑")
    @PostMapping(value = "addOrEdit")
    public Result<RoleEntity> addOrEdit(@RequestBody RoleDTO roleDTO, BindingResult result) {

        return roleService.addOrEdit(roleDTO);
    }

    /**
     * 批量删除角色.
     *
     * @param form 主键ids.
     * @return 操作信息.
     */
    @PostMapping(value = "deleteRoles")
    @ApiOperation(value = "批量删除角色")
    public Result<String> deleteRoles(@RequestBody IdsForm form) {

        return roleService.deleteRoles(form.getIds());
    }

    /**
     * 获取用户树状数据.
     *
     * @param userId 用户.
     * @return 操作信息.
     */
    @GetMapping(value = "queryRoleTree")
    @ApiOperation(value = "获取用户树状数据")
    public Result<List<TreeNode>> queryRoleTree(@ApiParam(name = "userId", value = "用户id") @RequestParam(value = "userId") String userId) {

        return Result.of(roleService.queryRoleTree(userId));
    }

    /**
     * 处理角色权限.
     *
     * @param dto 传输类.
     * @return 返回信息.
     */
    @PostMapping(value = "handleRolePermission")
    @ApiOperation(value = "处理角色权限")
    public Result<String> handleRolePermission(@RequestBody HandleTreeDTO dto) {

        return roleService.handleRolePermission(dto);
    }

    /**
     * 处理角色菜单.
     *
     * @param dto 传输类.
     * @return 返回信息.
     */
    @PostMapping(value = "handleRoleMenu")
    @ApiOperation(value = "处理角色菜单")
    public Result<String> handleRoleMenu(@RequestBody HandleTreeDTO dto) {

        return roleService.handleRoleMenu(dto);
    }
}
