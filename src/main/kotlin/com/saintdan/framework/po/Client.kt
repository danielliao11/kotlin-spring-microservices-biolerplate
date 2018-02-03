package com.saintdan.framework.po

import com.saintdan.framework.constant.CommonsConstant
import com.saintdan.framework.listener.UpdateListener
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import javax.persistence.*

/**
 * Authorized client, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "clients")
@EntityListeners(UpdateListener::class)
data class Client(
    @GenericGenerator(name = "clientSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          Parameter(name = "sequence_name", value = "clients_seq"),
          Parameter(name = "initial_value", value = "1"),
          Parameter(name = "increment_size", value = "1")])
    @Id
    @GeneratedValue(generator = "clientSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(length = 50)
    val name: String = "",

    @Column(length = 50)
    val clientIdAlias: String = "",

    @Column(length = 100)
    val resourceIdStr: String = "",

    @Column(length = 100)
    val clientSecretAlias: String = "",

    /**
     * Available values: read, write
     */
    @Column(length = 100)
    val scopeStr: String = "",

    /**
     * grant types include "authorization_code", "password", "assertion", and "refresh_token". Default
     * description is "authorization_code,refresh_token".
     */
    @Column(length = 100)
    val authorizedGrantTypeStr: String = "",

    /**
     * The redirect URI(s) established during registration (optional, comma separated).
     */
    @Column(length = 1024)
    val registeredRedirectUriStr: String? = "",

    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <pre>
     * For example: USER
    </pre> *
     */
    @Column(length = 500)
    val authoritiesStr: String = "",

    /**
     * The access token validity period in seconds (optional). If unspecified a global default will be
     * applied by the token services.
     */
    val accessTokenValiditySecondsAlias: Int = 1800,

    /**
     * The refresh token validity period in seconds (optional). If unspecified a global default will
     * be applied by the token services.
     */
    val refreshTokenValiditySecondsAlias: Int = 3600,

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be
     * useful, for example, for storing descriptive information.
     */
    val additionalInformationStr: String? = "",

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    val createdBy: Long = 0,

    @Column(nullable = false)
    var lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    var lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    var version: Int = 0
) : ClientDetails {
  companion object {
    private const val serialVersionUID = 6500601540965188191L
  }

  override fun getClientId(): String {
    return clientIdAlias
  }

  override fun getResourceIds(): Set<String> {
    return str2Set(resourceIdStr)
  }

  override fun isSecretRequired(): Boolean {
    return true
  }

  override fun getClientSecret(): String {
    return clientSecretAlias
  }

  override fun isScoped(): Boolean {
    return true
  }

  override fun getScope(): Set<String> {
    return str2Set(scopeStr)
  }

  override fun getAuthorizedGrantTypes(): Set<String> {
    return str2Set(authorizedGrantTypeStr)
  }

  override fun getRegisteredRedirectUri(): Set<String> {
    return if (StringUtils.isBlank(registeredRedirectUriStr)) {
      emptySet()
    } else {
      str2Set(registeredRedirectUriStr!!)
    }
  }

  override fun getAuthorities(): Collection<GrantedAuthority> {
    return authorizedGrantTypeStr.split(CommonsConstant.COMMA)
        .map { SimpleGrantedAuthority(it) }
  }

  override fun getAccessTokenValiditySeconds(): Int? {
    return accessTokenValiditySecondsAlias
  }

  override fun getRefreshTokenValiditySeconds(): Int? {
    return refreshTokenValiditySecondsAlias
  }

  override fun isAutoApprove(scope: String): Boolean {
    return false
  }

  override fun getAdditionalInformation(): Map<String, Any>? {
    return null
  }

  private fun str2Set(str: String): Set<String> {
    return if (StringUtils.isBlank(str)) emptySet() else str.split(CommonsConstant.COMMA).toHashSet()
  }

  override fun equals(o: Any?): Boolean {
    if (this === o) return true

    if (o == null || javaClass != o.javaClass) return false

    val client = o as Client?

    return EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(id, client!!.id)
        .append(accessTokenValiditySecondsAlias, client.accessTokenValiditySecondsAlias)
        .append(refreshTokenValiditySecondsAlias, client.refreshTokenValiditySecondsAlias)
        .append(createdAt, client.createdAt)
        .append(createdBy, client.createdBy)
        .append(lastModifiedAt, client.lastModifiedAt)
        .append(lastModifiedBy, client.lastModifiedBy)
        .append(version, client.version)
        .append(clientIdAlias, client.clientIdAlias)
        .append(resourceIdStr, client.resourceIdStr)
        .append(clientSecretAlias, client.clientSecretAlias)
        .append(scopeStr, client.scopeStr)
        .append(authorizedGrantTypeStr, client.authorizedGrantTypeStr)
        .append(registeredRedirectUriStr, client.registeredRedirectUriStr)
        .append(authoritiesStr, client.authoritiesStr)
        .append(additionalInformationStr, client.additionalInformationStr)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(clientIdAlias)
        .append(resourceIdStr)
        .append(clientSecretAlias)
        .append(scopeStr)
        .append(authorizedGrantTypeStr)
        .append(registeredRedirectUriStr)
        .append(authoritiesStr)
        .append(accessTokenValiditySecondsAlias)
        .append(refreshTokenValiditySecondsAlias)
        .append(additionalInformationStr)
        .append(createdAt)
        .append(createdBy)
        .append(lastModifiedAt)
        .append(lastModifiedBy)
        .append(version)
        .toHashCode()
  }
}