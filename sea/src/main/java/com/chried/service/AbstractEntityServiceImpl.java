package com.chried.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.enums.EnumEntityStatus;
import com.chried.mapper.AbstractEntityMapper;
import com.chried.model.entity.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 抽象实体类实现.
 *
 * @param <T>
 * @author chried
 */
public abstract class AbstractEntityServiceImpl<M extends AbstractEntityMapper<T>, T extends AbstractEntity> extends ServiceImpl<M, T> implements AbstractEntityService<T> {

    /**
     * 初始化一些操作.
     *
     * @param entity 实体.
     */
    private void init(T entity) {

        if (entity.getId() == null) {
            // 如果创建时间不存在.赋值当前时间.
            if (entity.getCreateDate() == null) {
                entity.setCreateDate(LocalDateTime.now());
            }
            // 如果不存在，那么跟创建时间一致.
            if (entity.getUpdateDate() == null) {
                entity.setUpdateDate(entity.getCreateDate());
            }

            entity.setEdition(UUID.randomUUID().toString());
            entity.setStatus(EnumEntityStatus.ACTIVE.name());
        } else {

            entity.setUpdateDate(LocalDateTime.now());
            entity.setEdition(UUID.randomUUID().toString());
        }
    }

    @Override
    public boolean save(T entity) {

        init(entity);
        return super.save(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {

        init(entity);
        return super.update(entity, updateWrapper);
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Override
    public boolean saveOrUpdate(T entity) {

        init(entity);

        return super.saveOrUpdate(entity);
    }
}
