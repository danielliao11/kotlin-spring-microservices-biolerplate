package com.saintdan.framework.config.custom

import com.saintdan.framework.component.CustomPasswordEncoder
import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.enums.ErrorType
import com.saintdan.framework.repo.OauthAccessTokenRepository
import com.saintdan.framework.repo.OauthRefreshTokenRepository
import com.saintdan.framework.repo.UserRepository
import com.saintdan.framework.tool.LoginUtils
import com.saintdan.framework.tool.RemoteAddressUtils
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@Service
class CustomAuthenticationProvider(
    private val userRepository: UserRepository,
    private val accessTokenRepository: OauthAccessTokenRepository,
    private val refreshTokenRepository: OauthRefreshTokenRepository,
    private val request: HttpServletRequest,
    private val customPasswordEncoder: CustomPasswordEncoder,
    private val logHelper: LogHelper) : AuthenticationProvider {

  override fun authenticate(authentication: Authentication?): Authentication {
    val token = authentication as UsernamePasswordAuthenticationToken
    val clientId = LoginUtils.getClientId(request)
    val username = token.name
    // Find user.
    val user = userRepository.findByUsr(username).orElse(null) ?: throw BadCredentialsException(ErrorType.LOG0001.description)
    // Get token object
    val accessToken = accessTokenRepository.findByUserName(username).orElse(null)
    if (!customPasswordEncoder.matches(token.credentials.toString(), user.pwd)) {
      throw BadCredentialsException(ErrorType.LOG0002.description)
    }
    if (!user.isEnabled) {
      throw BadCredentialsException(ErrorType.LOG0003.description)
    } else if (!user.isAccountNonExpired) {
      throw BadCredentialsException(ErrorType.LOG0004.description)
    } else if (!user.isAccountNonLocked) {
      throw BadCredentialsException(ErrorType.LOG0005.description)
    } else if (!user.isCredentialsNonExpired) {
      throw BadCredentialsException(ErrorType.LOG0006.description)
    }
    // Get client ip address.
    val ip = RemoteAddressUtils.getRealIp(request)
    // Delete token if repeat login.
    if (user.ip != null && user.ip != ip && accessToken != null) {
      accessTokenRepository.delete(accessToken)
      refreshTokenRepository.findByTokenId(accessToken.tokenId)
          .ifPresent { refreshTokenRepository.delete(it) }
    }
    // Save user login info.
    user.copy(
        ip = ip,
        lastLoginAt = System.currentTimeMillis()
    )
    userRepository.save(user)
    // Log login information
    logHelper.log(HttpMethod.POST, username, ip, clientId, "login")
    return UsernamePasswordAuthenticationToken(user, user.pwd, user.authorities)
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
  }
}