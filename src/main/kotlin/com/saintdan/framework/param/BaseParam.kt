package com.saintdan.framework.param

import io.swagger.annotations.ApiModelProperty

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
open class BaseParam(
    @ApiModelProperty(hidden = true)
    val pageNo: Int? = null,

    @ApiModelProperty(hidden = true)
    val pageSize: Int? = null,

    @ApiModelProperty(hidden = true)
    private val sortBy: String? = null
)