package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import org.springframework.http.HttpMethod

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class ClientParam(

    @NotNullField(method = [(HttpMethod.POST)], message = "nickname cannot be null.")
    var name: String? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "scope cannot be null.")
    var scope: String? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "grantType cannot be null.")
    var grantType: String? = null,
    var accessTokenValiditySeconds: Int? = null,
    var refreshTokenValiditySeconds: Int? = null
) : BaseParam()