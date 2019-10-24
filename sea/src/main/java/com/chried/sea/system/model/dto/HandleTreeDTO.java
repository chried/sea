package com.chried.sea.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 处理授权传输类.
 * <pre>
 *     用一个主键跟多个从键.
 * </pre>
 *
 * @author chried
 */
@ApiModel(description = "用户授角色传输类")
@Data
public class HandleTreeDTO implements Serializable {

    /**
     * 主键id.
     */
    @ApiModelProperty(name = "userId", value = "主键id")
    @NotNull(message = "主键不能为空")
    private String id;

    /**
     * 从键ids.
     */
    @ApiModelProperty(name = "roleIds", value = "从键ids")
    @NotNull(message = "从键不能为空")
    private List<String> ids;

}
