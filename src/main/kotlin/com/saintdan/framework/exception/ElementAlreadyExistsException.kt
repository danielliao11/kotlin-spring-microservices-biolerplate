package com.saintdan.framework.exception

import com.saintdan.framework.enums.ErrorType

/**
 * Throw exception when resource already exists
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
class ElementAlreadyExistsException(
    override val message: String? = "Element already exists",
    val code: String = ErrorType.SYS0003.name
) : Exception()