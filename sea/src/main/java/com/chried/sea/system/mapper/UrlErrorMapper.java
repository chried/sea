package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.UrlErrorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * url错误mapper.
 *
 * @author chried
 */
@Mapper
@Repository
public interface UrlErrorMapper extends BaseMapper<UrlErrorEntity> {
}
