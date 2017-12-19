package com.saintdan.framework.enums

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/12/2017
 * @since JDK1.8
 */
enum class ErrorType (val description: String) {

  // System
  SYS0001("System error."),
  SYS0002("Param cannot be null."),
  SYS0003("Element already exists."),
  SYS0004("No such element."),
  SYS0005("Invalid token."),

  // Login
  LOG0001("User not exists."),
  LOG0002("Wrong password."),
  LOG0003("Disabled account."),
  LOG0004("Expired account."),
  LOG0005("Locked account."),
  LOG0006("Expired credentials."),
  LOG0007("Illegal token type."),

  // Unknown error.
  UNKNOWN("unknown error.")
}