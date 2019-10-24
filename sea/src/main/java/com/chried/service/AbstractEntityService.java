package com.chried.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.model.entity.AbstractEntity;

/**
 * 抽象实体接口.
 * <pre>
 *     扩展Iservice接口.
 * </pre>
 *
 * @author chried
 */
public interface AbstractEntityService<T extends AbstractEntity> extends IService<T> {

}
