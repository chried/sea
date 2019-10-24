package com.chried.sea.controller.commons;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.core.param.PageParameter;
import com.chried.core.param.Result;
import com.chried.sea.system.model.dto.UrlErrorDTO;
import com.chried.sea.system.model.entity.UrlErrorEntity;
import com.chried.sea.system.service.UrlErrorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 错误日志控制器，记录错误日志.
 *
 * @author chried
 */
@RestController
@RequestMapping(value = "url/error")
public class UrlErrorController {

    @Resource
    private UrlErrorService urlErrorService;

    /**
     * 报错错误日志信息.
     *
     * @param dto    传输类.
     * @param result 验证封装.
     * @return 操作信息.
     */
    @PostMapping(value = "save_error_logger")
    public Result<String> saveErrorLogger(@Validated @RequestBody UrlErrorDTO dto, BindingResult result) {

        return Result.of(urlErrorService.saveErrorLogger(dto));
    }

    /**
     * 分页查询错误日志.
     *
     * @return 分页信息.
     */
    @ApiOperation(value = "分页查询错误日志")
    @PostMapping(value = "query")
    public Page<UrlErrorEntity> query(@RequestBody PageParameter<UrlErrorEntity> page) {

        return (Page<UrlErrorEntity>) urlErrorService.page(page.createPage(), page.createWrapper());
    }
}
