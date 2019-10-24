package com.chried.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 定义顶级实体类.
 *
 * @author chried
 */
@ApiModel(description = "顶级实体类")
@Setter
@Getter
public abstract class BaseEntity<T extends BaseEntity<T>> extends Model<T> {

    /**
     * 主键ID.
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(name = "id", value = "主键")
    private String id;

}
