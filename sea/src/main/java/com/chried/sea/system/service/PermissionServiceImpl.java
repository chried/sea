package com.chried.sea.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.exception.SystemException;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.mapper.PermissionMapper;
import com.chried.sea.system.model.dto.PermissionDTO;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务实现.
 *
 * @author chried
 */
@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 添加/编辑权限.
     *
     * @param permission 权限传输类.
     * @return 权限.
     */
    @Override
    public Result<PermissionEntity> addOrUpdate(PermissionDTO permission) {
        log.info("添加/编辑权限");

        PermissionEntity permissionEntity = permissionMapper.getByParams(permission.getName(), permission.getSign(), permission.getUrl());
        if (permissionEntity != null) {

            if (permission.getId() == null) {

                throw new SystemException("名称/标识/连接必须唯一");
            }
            if (!permissionEntity.getId().equals(permission.getId())) {

                throw new SystemException("名称/标识/连接必须唯一");
            }
        }

        if (permissionEntity == null) {
            permissionEntity = new PermissionEntity();
        }

        BeanUtils.copyProperties(permission, permissionEntity);

        this.saveOrUpdate(permissionEntity);

        return Result.of(permissionEntity);
    }

    /**
     * 批量删除权限.
     *
     * @param ids 权限ids.
     * @return 删除信息.
     */
    @Override
    public Result<String> deletePermissions(Collection<String> ids) {
        log.info("批量删除权限...");

        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("permission_id", ids);

        List<RolePermissionEntity> rolePermissionEntities = rolePermissionService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(rolePermissionEntities)) {

            throw new SystemException("权限关联角色，不允许删除");
        }

        this.removeByIds(ids);

        return Result.of("删除成功");
    }

    /**
     * 查询所有权限.
     * <pre>
     *     根据role选中.
     * </pre>
     *
     * @param roleId 角色id.
     * @return 权限信息.
     */
    @Override
    public List<TreeNode> queryPermissionTree(String roleId) {
        log.info("获取权限树状结构...");
        // 获取所有的权限.
        List<PermissionEntity> permissionEntities = this.list();
        // 角色配置好的权限.
        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("role_id", roleId);
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionService.list(queryWrapper);

        List<String> permissionIds = rolePermissionEntities.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());

        return permissionEntities.stream().map(s -> {
            TreeNode treeNode = new TreeNode();
            if (permissionIds.contains(s.getId())) {
                treeNode.setChecked(true);
            }
            treeNode.setId(s.getId());
            treeNode.setCode(s.getSign());
            treeNode.setTitle(s.getName() + "(" + s.getUrl() + ")");
            return treeNode;
        }).collect(Collectors.toList());
    }
}
