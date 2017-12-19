package com.saintdan.framework.tool

import com.saintdan.framework.constant.AuthorityConstant.BASIC
import com.saintdan.framework.constant.CommonsConstant
import com.saintdan.framework.enums.GrantType
import com.saintdan.framework.exception.IllegalTokenException
import com.saintdan.framework.param.LoginParam
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpHeaders.AUTHORIZATION
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
object LoginUtils {

  private val USERNAME = "username"
  private val PASSWORD = "password"
  private val REFRESH_TOKEN = "refresh_token"
  private val GRANT_TYPE = "grant_type"
  private val SCOPE = "scope"
  private val READ = "read"

  @Throws(IllegalTokenException::class)
  fun getClientId(request: HttpServletRequest): String {
    val auth = request.getHeader(AUTHORIZATION)
    if (auth == null || !auth.toLowerCase().contains(BASIC)) {
      throw IllegalTokenException("Basic token acceptable")
    }
    val clientId64 = String(Base64.decodeBase64(auth.replace(BASIC, CommonsConstant.BLANK)))
    return clientId64.trim().substring(0, clientId64.indexOf(CommonsConstant.COLON))
  }

  fun getParams(param: LoginParam): Map<String, String> {
    val map = HashMap<String, String>()
    if (StringUtils.isNotBlank(param.usr)) {
      map.put(USERNAME, param.usr!!)
    }
    if (StringUtils.isNotBlank(param.pwd)) {
      map.put(PASSWORD, param.pwd!!)
      map.put(GRANT_TYPE, GrantType.PASSWORD.description)
    }
    if (StringUtils.isNotBlank(param.refreshToken)) {
      map.put(REFRESH_TOKEN, param.refreshToken!!)
      map.put(GRANT_TYPE, GrantType.REFRESH_TOKEN.description)
    }
    map.put(SCOPE, READ)
    return map
  }
}