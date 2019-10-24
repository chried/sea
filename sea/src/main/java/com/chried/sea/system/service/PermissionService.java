package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.core.param.Result;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.dto.PermissionDTO;
import com.chried.sea.system.model.entity.PermissionEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 权限服务.
 *
 * @author chried
 */
public interface PermissionService extends IService<PermissionEntity> {

    /**
     * 添加/编辑权限.
     *
     * @param permission 权限传输类.
     * @return 权限.
     */
    Result<PermissionEntity> addOrUpdate(PermissionDTO permission);

    /**
     * 批量删除权限.
     *
     * @param ids 权限ids.
     * @return 删除信息.
     */
    Result<String> deletePermissions(Collection<String> ids);

    /**
     * 查询所有权限.
     * <pre>
     *     根据role选中.
     * </pre>
     *
     * @param roleId 角色id.
     * @return 权限信息.
     */
    List<TreeNode> queryPermissionTree(String roleId);
}
