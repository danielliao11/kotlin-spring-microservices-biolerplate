package com.saintdan.framework.annotation

import org.springframework.http.HttpMethod
import javax.validation.Constraint

/**
 * Size validate.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
@Retention()
@MustBeDocumented
@Constraint(validatedBy = [])
annotation class SizeField(
    val message: String = "{javax.validation.constraints.Size.message}",
    val min: Int = 0,
    val max: Int = Integer.MAX_VALUE,
    val method: Array<HttpMethod> = [(HttpMethod.GET)] // For resource access.
)