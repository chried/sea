package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * url错误日志表.
 *
 * @author chried
 */
@Setter
@Getter
@TableName(value = "url_error")
@ApiModel(description = "url错误日志表")
public class UrlErrorEntity extends BaseEntity<UrlErrorEntity> {

    /**
     * 请求类型.
     */
    @ApiModelProperty(name = "type", value = "请求类型", example = "ajax")
    @TableField(value = "error_type")
    private String errorType;

    /**
     * 错误码.
     */
    @ApiModelProperty(name = "code", value = "错误码")
    @TableField(value = "error_code")
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
    private String url;

    /**
     * 创建时间.
     */
    @ApiModelProperty(name = "createDate", value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间.
     */
    @ApiModelProperty(name = "updateDate", value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_date")
    private LocalDateTime updateDate;

}
