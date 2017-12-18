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
    val id: Long? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "nickname cannot be null.")
    val name: String? = null, // role's nickname
    val description: String? = null
) : BaseParam()