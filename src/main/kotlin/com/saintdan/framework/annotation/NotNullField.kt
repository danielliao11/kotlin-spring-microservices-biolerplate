package com.saintdan.framework.annotation

import org.springframework.http.HttpMethod

/**
 * Null validate.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
@Target(AnnotationTarget.FIELD)
@Retention()
annotation class NotNullField(
    val message: String = "{javax.validation.constraints.NotNull.message}",
    val method: Array<HttpMethod> = [(HttpMethod.GET)] // For resource access.
)