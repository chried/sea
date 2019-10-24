package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * role mapper.
 *
 * @author chried
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {
}
