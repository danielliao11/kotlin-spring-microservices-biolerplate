package com.saintdan.framework.enums

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
enum class GrantType (val description: String) {
  PASSWORD("password"),
  REFRESH_TOKEN("refresh_token"),
  AUTHORIZATION_CODE("authorization_code")
}