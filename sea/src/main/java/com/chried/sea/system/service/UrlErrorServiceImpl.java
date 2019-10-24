package com.chried.sea.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.sea.system.mapper.UrlErrorMapper;
import com.chried.sea.system.model.dto.UrlErrorDTO;
import com.chried.sea.system.model.entity.UrlErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * url错误服务实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class UrlErrorServiceImpl extends ServiceImpl<UrlErrorMapper, UrlErrorEntity> implements UrlErrorService {

    @Resource
    private UrlErrorMapper urlErrorMapper;

    /**
     * 保存错误信息.
     *
     * @param dto url.
     * @return 操作信息.
     */
    @Override
    public String saveErrorLogger(UrlErrorDTO dto) {
        log.info("保存错误信息...");

        /*
         * 每个url请求一次最新的就可以了的，没有必要每次错误都记录，没什么参考意义.
         */
        QueryWrapper<UrlErrorEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("url", dto.getUrl());

        UrlErrorEntity urlErrorEntity = this.getOne(queryWrapper);
        // 获取当前时间.
        LocalDateTime now = LocalDateTime.now();

        if (urlErrorEntity == null) {
            urlErrorEntity = new UrlErrorEntity();
            urlErrorEntity.setCreateDate(now);
        }
        BeanUtils.copyProperties(dto, urlErrorEntity);

        urlErrorEntity.setUpdateDate(now);

        return this.saveOrUpdate(urlErrorEntity) ? "保存成功" : "保存失败";
    }
}
