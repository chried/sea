package com.chried.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 定义抽象entity.
 *
 * @author chried
 */
@ApiModel(description = "抽象entity")
@Setter
@Getter
public abstract class AbstractEntity<T extends AbstractEntity<T>> extends BaseEntity<T> {

    /**
     * 创建时间.
     */
    @ApiModelProperty(name = "createDate", value = "创建时间")
    @TableField(value = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 更新时间.
     */
    @ApiModelProperty(name = "updateDate", value = "更新时间")
    @TableField(value = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 状态.
     */
    @TableField(value = "x_status")
    @ApiModelProperty(name = "status", value = "状态")
    private String status;

    /**
     * 版本号.
     */
    @ApiModelProperty(name = "edition", value = "版本号")
    @TableField(value = "x_edition")
    private String edition;
}
