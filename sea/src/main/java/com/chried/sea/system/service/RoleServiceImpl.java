package com.chried.sea.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.exception.SystemException;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.mapper.RoleMapper;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.dto.RoleDTO;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.RoleMenuEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import com.chried.sea.system.model.entity.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色服务实现.
 *
 * @author chried
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 添加或者编辑.
     *
     * @param roleDTO 角色传输类.
     * @return 实体类.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<RoleEntity> addOrEdit(RoleDTO roleDTO) {
        log.info("添加/编辑角色...");
        // 验证名称是否重复.
        QueryWrapper<RoleEntity> nameQueryWrapper = new QueryWrapper<>();
        nameQueryWrapper.eq("name", roleDTO.getName());
        RoleEntity nameRole = this.getOne(nameQueryWrapper);

        QueryWrapper<RoleEntity> signQueryWrapper = new QueryWrapper<>();
        signQueryWrapper.eq("sign", roleDTO.getSign());
        RoleEntity signRole = this.getOne(signQueryWrapper);

        if (StringUtils.isNotBlank(roleDTO.getId())) {

            if (nameRole != null) {
                if (!nameRole.getId().equals(roleDTO.getId())) {
                    throw new SystemException("角色名称[" + roleDTO.getName() + "]重复");
                }
            }
            if (signRole != null) {
                if (!signRole.getId().equals(roleDTO.getId())) {
                    throw new SystemException("角色标识[" + roleDTO.getSign() + "]重复");
                }
            }

        } else {
            if (nameRole != null) {
                throw new SystemException("角色名称[" + roleDTO.getName() + "]重复");
            }
            if (signRole != null) {
                throw new SystemException("角色标识[" + roleDTO.getSign() + "]重复");
            }
        }

        // 验证标志是否重复.

        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleDTO, roleEntity);

        this.saveOrUpdate(roleEntity);

        return Result.of(roleEntity);
    }

    /**
     * 批量删除角色.
     *
     * @param ids 角色ids.
     * @return 删除消息.
     */
    @Override
    public Result<String> deleteRoles(Collection<String> ids) {
        log.info("批量删除角色...");

        if (CollectionUtils.isEmpty(ids)) {
            throw new SystemException("请至少选择一项删除");
        }

        QueryWrapper<UserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        userRoleEntityQueryWrapper.in("role_id", ids);
        // 排除过期角色.
        userRoleEntityQueryWrapper.ge("expire_date", LocalDateTime.now());
        List<UserRoleEntity> userRoleEntities = userRoleService.list(userRoleEntityQueryWrapper);

        if (CollectionUtils.isNotEmpty(userRoleEntities)) {

            throw new SystemException("删除失败，该角色关联数据");
        }

        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        roleMenuEntityQueryWrapper.in("role_id", ids);

        List<RoleMenuEntity> roleMenuEntities = roleMenuService.list(roleMenuEntityQueryWrapper);

        if (CollectionUtils.isNotEmpty(roleMenuEntities)) {

            throw new SystemException("删除失败，该角色关联数据");
        }

        this.removeByIds(ids);

        return Result.of("删除成功");
    }

    /**
     * 获取所有角色.
     *
     * @param userId 用户id.
     * @return 角色树状.
     */
    @Override
    public List<TreeNode> queryRoleTree(String userId) {
        log.info("获取角色树状数据...");

        List<RoleEntity> roles = this.list();

        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", userId);
        queryWrapper.and((s) -> {
            s.isNull("expire_date");
        });
        queryWrapper.or(s -> {
            s.ge("expire_date", LocalDateTime.now());
        });
        List<UserRoleEntity> userRoleEntities = userRoleService.list(queryWrapper);

        List<String> roleIds = userRoleEntities.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());

        return roles.stream().map(s -> {
            TreeNode treeNode = new TreeNode();
            if (roleIds.contains(s.getId())) {
                treeNode.setChecked(true);
            }
            treeNode.setId(s.getId());
            treeNode.setCode(s.getSign());
            treeNode.setTitle(s.getName());
            return treeNode;
        }).collect(Collectors.toList());
    }

    /**
     * 处理角色权限.
     *
     * @param dto 传输类.
     * @return 操作信息.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> handleRolePermission(HandleTreeDTO dto) {
        log.info("处理角色权限...");

        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", dto.getId());
        rolePermissionService.remove(queryWrapper);

        List<RolePermissionEntity> rolePermissionEntities = dto.getIds().stream().map(s -> {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setRoleId(dto.getId());
            rolePermissionEntity.setPermissionId(s);
            return rolePermissionEntity;
        }).collect(Collectors.toList());

        rolePermissionService.saveBatch(rolePermissionEntities);

        return Result.of("操作成功");
    }

    /**
     * 处理角色菜单.
     *
     * @param dto 传输类.
     * @return 操作信息.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> handleRoleMenu(HandleTreeDTO dto) {
        log.info("处理角色菜单...");

        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", dto.getId());
        roleMenuService.remove(queryWrapper);

        List<RoleMenuEntity> roleMenuEntities = dto.getIds().stream().map(s -> {
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(dto.getId());
            roleMenuEntity.setMenuId(s);
            return roleMenuEntity;
        }).collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenuEntities);

        return Result.of("操作成功");
    }
}
