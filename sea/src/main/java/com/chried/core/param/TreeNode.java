package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 定义树状结构.
 *
 * @author chried
 */
@ApiModel(description = "树状结构")
@Data
public class TreeNode implements Serializable {

    /**
     * id.
     */
    @ApiModelProperty(name = "id", value = "主键id")
    private String id;

    /**
     * 编号.
     */
    @ApiModelProperty(name = "code", value = "编号")
    private String code;

    /**
     * 标题.
     */
    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    /**
     * 是否展开.
     */
    @ApiModelProperty(name = "expand", value = "是否展开")
    private boolean expand;

    /**
     * 是否选中子节点
     */
    @ApiModelProperty(name = "selected", value = "是否选中子节点")
    private boolean selected;

    /**
     * 是否勾选(如果勾选，子节点也会全部勾选).
     */
    @ApiModelProperty(name = "checked", value = "是否勾选(如果勾选，子节点也会全部勾选)")
    private boolean checked;

    /**
     * 子节点.
     */
    @ApiModelProperty(name = "children", value = "子节点")
    private List<TreeNode> children;
}
