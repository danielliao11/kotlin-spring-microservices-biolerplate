package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpMethod

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class ClientParam(
    @ApiModelProperty(hidden = true)
    val id: Long? = 0,

    @NotNullField(method = [(HttpMethod.POST)], message = "nickname cannot be null.")
    val name: String? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "scope cannot be null.")
    val scope: String? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "grantType cannot be null.")
    val grantType: String? = null,
    val accessTokenValiditySeconds: Int? = null,
    val refreshTokenValiditySeconds: Int? = null
) : BaseParam()