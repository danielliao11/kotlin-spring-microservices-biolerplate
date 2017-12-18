package com.saintdan.framework.enums

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/12/2017
 * @since JDK1.8
 */
enum class ErrorType constructor(private val description: String) {

  // System
  SYS0001("System error."),
  SYS0002("Param cannot be null."),
  SYS0003("Element already exists."),
  SYS0004("No such element."),


  // Unknown error.
  UNKNOWN("unknown error.")
}