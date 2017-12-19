package com.saintdan.framework.service.impl

import com.saintdan.framework.param.LoginParam
import com.saintdan.framework.service.LoginService
import com.saintdan.framework.tool.LoginUtils
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Service
class LoginServiceImpl(
    private val env: Environment,
    private val tokenEndpoint: TokenEndpoint) : LoginService {
  override fun login(param: LoginParam, request: HttpServletRequest): ResponseEntity<OAuth2AccessToken> {
    return execute(param, request)
  }

  override fun refresh(param: LoginParam, request: HttpServletRequest): ResponseEntity<OAuth2AccessToken> {
    return execute(param, request)
  }

  private fun execute(param: LoginParam, request: HttpServletRequest): ResponseEntity<OAuth2AccessToken> {
    val authorityProp ="client.authorities"
    val clientAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(env.getProperty(authorityProp))
    val token = UsernamePasswordAuthenticationToken(LoginUtils.getClientId(request), "", clientAuthorities)
    val params = LoginUtils.getParams(param)
    return tokenEndpoint.postAccessToken(token, params)
  }
}