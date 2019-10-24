package com.chried.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.model.entity.AbstractEntity;

/**
 * 扩展baseMapper.
 *
 * <pre>
 *     继承AbstractEntity实体类的mapper才继承此接口.
 * </pre>
 *
 * @param <T> 泛型.
 * @author chried
 */
public interface AbstractEntityMapper<T extends AbstractEntity> extends BaseMapper<T> {
}
