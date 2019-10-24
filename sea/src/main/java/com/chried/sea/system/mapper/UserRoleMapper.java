package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色mapper.
 *
 * @author chried
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    /**
     * 根据用户查询角色.
     *
     * @param userId 用户id.
     * @return 角色.
     */
    List<RoleEntity> selectRoleByUserId(@Param("userId") String userId);
}
