package com.saintdan.framework.constant

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
class AuthorityConstant {
  companion object {
    const val RESOURCE_ID = "api"
    const val SCOPE = "read"
    const val GRANT_TYPE = "password,refresh_token"
    const val AUTHORITY = "USER"
    const val BASIC = "basic"
    const val BEARER = "bearer"
  }
}