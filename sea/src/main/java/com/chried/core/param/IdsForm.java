package com.chried.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * ids传输类.
 *
 * @author chried
 */
@Data
@ApiModel(description = "ids传输类")
public class IdsForm implements Serializable {

    /**
     * 主键ids.
     */
    @ApiModelProperty(name = "ids", value = "主键ids")
    @NotNull(message = "主键不能为空")
    private Collection<String> ids;
}
