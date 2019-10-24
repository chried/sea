package com.chried.sea.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.core.param.IdsForm;
import com.chried.core.param.PageParameter;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.PermissionDTO;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限控制器.
 *
 * @author chried
 */
@RequestMapping(value = "admin/permission")
@RestController(value = "admin$permission")
@Api(tags = "权限控制器")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 添加或者编辑.
     *
     * @param permission 权限传输类.
     * @return 权限类.
     */
    @PostMapping(value = "addOrUpdate")
    @ApiOperation(value = "添加/编辑")
    public Result<PermissionEntity> addOrUpdate(@RequestBody PermissionDTO permission) {

        return permissionService.addOrUpdate(permission);
    }


    /**
     * 分页查询.
     *
     * @return 分页.
     */
    @PostMapping(value = "query")
    @ApiOperation(value = "分页查询")
    public Page<PermissionEntity> query(@RequestBody PageParameter<PermissionEntity> page) {

        return (Page<PermissionEntity>) permissionService.page(page.createPage(), page.createWrapper());
    }

    /**
     * 批量删除权限.
     *
     * @param form ids.
     * @return 删除信息.
     */
    @PostMapping(value = "deletePermissions")
    @ApiOperation(value = "批量删除权限")
    public Result<String> deletePermissions(@RequestBody IdsForm form) {

        return permissionService.deletePermissions(form.getIds());
    }

    /**
     * 查询角色树.
     *
     * @param roleId 角色id.
     * @return 树.
     */
    @GetMapping(value = "queryPermissionTree")
    @ApiOperation(value = "查询角色树")
    public Result<List<TreeNode>> queryPermissionTree(@ApiParam(name = "roleId", value = "角色id") @RequestParam(value = "roleId") String roleId) {

        return Result.of(permissionService.queryPermissionTree(roleId));
    }
}
