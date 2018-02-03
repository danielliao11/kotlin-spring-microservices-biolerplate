package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import org.springframework.http.HttpMethod

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class RoleParam(

    @NotNullField(method = [(HttpMethod.POST)], message = "name cannot be null.")
    var name: String? = null,
    var description: String? = null,
    var resourceIds: Set<Long>? = null
) : BaseParam()