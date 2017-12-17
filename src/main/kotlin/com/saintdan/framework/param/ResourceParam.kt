package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpMethod

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class ResourceParam(
    @ApiModelProperty(hidden = true)
    private val id: Long? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "name cannot be null.")
    private val name: String? = null, // role's name
    private val description: String? = null
) : BaseParam()