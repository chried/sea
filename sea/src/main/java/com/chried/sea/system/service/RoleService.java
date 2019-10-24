package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.dto.RoleDTO;
import com.chried.sea.system.model.entity.RoleEntity;

import java.util.Collection;
import java.util.List;

/**
 * role服务.
 *
 * @author chried
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 添加或者编辑.
     *
     * @param roleDTO 角色传输类.
     * @return 实体类.
     */
    Result<RoleEntity> addOrEdit(RoleDTO roleDTO);

    /**
     * 批量删除角色.
     *
     * @param ids 角色ids.
     * @return 删除消息.
     */
    Result<String> deleteRoles(Collection<String> ids);


    /**
     * 获取所有角色.
     *
     * @param userId 用户id.
     * @return 角色树状.
     */
    List<TreeNode> queryRoleTree(String userId);

    /**
     * 处理角色权限.
     *
     * @param dto 传输类.
     * @return 操作信息.
     */
    Result<String> handleRolePermission(HandleTreeDTO dto);

    /**
     * 处理角色菜单.
     *
     * @param dto 传输类.
     * @return 操作信息.
     */
    Result<String> handleRoleMenu(HandleTreeDTO dto);
}
