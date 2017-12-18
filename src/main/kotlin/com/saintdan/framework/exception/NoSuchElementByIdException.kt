package com.saintdan.framework.exception

import com.saintdan.framework.enums.ErrorType

/**
 * Throw exception when resource already exists
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
class NoSuchElementByIdException(
    override val message: String?,
    val code: String = ErrorType.SYS0004.name
) : NoSuchElementException()