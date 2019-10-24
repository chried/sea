package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * url错误传输类.
 *
 * @author chried
 */
@ApiModel(description = "url错误传输类")
@Data
public class UrlErrorDTO implements Serializable {

    /**
     * 请求类型.
     */
    @ApiModelProperty(name = "type", value = "请求类型", example = "ajax")
    @NotNull(message = "请求类型必须存在")
    private String errorType;

    /**
     * 错误码.
     */
    @ApiModelProperty(name = "code", value = "错误码")
    @NotNull(message = "错误码不能为空")
    private Integer errorCode;

    /**
     * 错误消息.
     */
    @ApiModelProperty(name = "msg", value = "错误信息")
    private String msg;

    /**
     * 错误url.
     */
    @ApiModelProperty(name = "url", value = "错误url")
    @NotNull(message = "错误url不能为空")
    private String url;
}
