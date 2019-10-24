package com.chried.sea.system.mapper;

import com.chried.mapper.AbstractEntityMapper;
import com.chried.sea.system.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * userMapper.
 *
 * @author chried
 */
@Repository
@Mapper
public interface UserMapper extends AbstractEntityMapper<UserEntity> {
}
