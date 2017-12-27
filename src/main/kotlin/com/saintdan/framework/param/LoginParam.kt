package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import com.saintdan.framework.annotation.SizeField
import org.springframework.http.HttpMethod

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class LoginParam(
    @NotNullField(method = [(HttpMethod.POST)], message = "usr cannot be null.")
    @SizeField(min = 4, max = 50, message = "usr must greater than or equal to 4 and less than or equal to 50.")
    val usr: String? = null,

    @NotNullField(method = [(HttpMethod.POST)], message = "pwd cannot be null.")
    @SizeField(min = 4, max = 16, message = "pwd must greater than or equal to 4 and less than or equal to 16.")
    val pwd: String? = null,

    @NotNullField(method = [(HttpMethod.PUT)], message = "refresh token cannot be null.")
    val refreshToken: String? = null
) {
  fun toMap(): HashMap<String, String?> {
    return hashMapOf(
        "usr" to usr,
        "pwd" to pwd,
        "refreshToken" to refreshToken
    )
  }
}