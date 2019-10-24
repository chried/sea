package com.chried.sea.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chried.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 消息实体类.
 *
 * @author chried
 */
@ApiModel(description = "消息实体类")
@Setter
@Getter
@TableName(value = "message")
public class MessageEntity extends BaseEntity<MessageEntity> {

    /**
     * 消息分类.
     */
    @ApiModelProperty(name = "type", value = "分类")
    private Integer type;

    /**
     * 标题.
     */
    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    /**
     * 内容.
     */
    @ApiModelProperty(name = "content", value = "内容")
    private String content;

    /**
     * 是否阅读.
     */
    @ApiModelProperty(name = "read", value = "是否阅读")
    private boolean read;

    /**
     * 权限.
     */
    @ApiModelProperty(name = "roleId", value = "权限", example = "拥有此权限的才能看到消息")
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 创建时间.
     */
    @ApiModelProperty(name = "createDate", value = "创建时间")
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 阅读时间.
     */
    @ApiModelProperty(name = "readDate", value = "阅读时间")
    @TableField(value = "read_date")
    private LocalDateTime readDate;

}
