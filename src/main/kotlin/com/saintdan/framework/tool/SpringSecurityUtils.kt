package com.saintdan.framework.tool

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
class SpringSecurityUtils {
  companion object {

    /**
     * Get current user's IP address.
     *
     * @return IP
     */
    fun getCurrentUserIp(): String {
      val authentication = getAuthentication() ?: return ""
      val details = authentication.details
      if (details is OAuth2AuthenticationDetails) {
        return details.remoteAddress
      }
      return if (details is WebAuthenticationDetails) {
        details.remoteAddress
      } else ""
    }

    /**
     * Get current user.
     *
     * @param <T> user details
     * @return user details
    </T> */
    fun <T : UserDetails> getCurrentUser(): T? {
      val authentication = getAuthentication() ?: return null
      val principal = authentication.principal
      return if (principal !is UserDetails) {
        null
      } else principal as T

    }

    /**
     * Get current username.
     *
     * @return current username
     */
    fun getCurrentUsername(): String {
      val authentication = getAuthentication()
      return if (authentication == null || authentication.principal == null) {
        ""
      } else authentication.name
    }

    /**
     * Get current client id.
     *
     * @return current client id
     */
    fun getCurrentClientId(): String {
      val authentication = (getAuthentication() as OAuth2Authentication?)!!
      return authentication.oAuth2Request.clientId
    }

    /**
     * Save user details to security context.
     *
     * @param userDetails user details
     * @param request     request
     */
    fun saveUserDetailsToContext(userDetails: UserDetails, request: HttpServletRequest?) {
      val authentication = PreAuthenticatedAuthenticationToken(
          userDetails,
          userDetails.password, userDetails.authorities)

      if (request != null) {
        authentication.details = WebAuthenticationDetails(request)
      }

      SecurityContextHolder.getContext().authentication = authentication
    }

    /**
     * Get Authentication
     *
     * @return authentication
     */
    private fun getAuthentication(): Authentication? {
      val context = SecurityContextHolder.getContext() ?: return null
      return context.authentication
    }
  }
}