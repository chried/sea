package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * id传输类.
 *
 * @author chried
 */
@Data
@ApiModel(description = "id传输类")
public class IdForm implements Serializable {

    /**
     * 主键ids.
     */
    @NotNull(message = "主键不能为空")
    @ApiModelProperty(name = "ids", value = "主键ids")
    private String id;
}
