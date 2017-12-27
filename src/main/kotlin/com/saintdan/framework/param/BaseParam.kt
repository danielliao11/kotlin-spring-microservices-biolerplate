package com.saintdan.framework.param

import io.swagger.annotations.ApiModelProperty

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
open class BaseParam(
    @ApiModelProperty(hidden = true)
    var pageNo: Int? = null,

    @ApiModelProperty(hidden = true)
    var pageSize: Int? = null,

    @ApiModelProperty(hidden = true)
    var sortBy: String? = null
)