package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.sea.system.model.dto.UrlErrorDTO;
import com.chried.sea.system.model.entity.UrlErrorEntity;

/**
 * url错误服务.
 *
 * @author chried
 */
public interface UrlErrorService extends IService<UrlErrorEntity> {

    /**
     * 保存错误信息.
     *
     * @param dto url.
     * @return 操作信息.
     */
    String saveErrorLogger(UrlErrorDTO dto);
}
