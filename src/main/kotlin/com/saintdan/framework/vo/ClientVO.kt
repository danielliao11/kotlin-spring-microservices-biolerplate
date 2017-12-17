package com.saintdan.framework.vo

import org.springframework.security.core.GrantedAuthority

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class ClientVO(
    private val id: Long? = null,
    val clientId: String? = null,
    val resourceIds: Set<String>? = null,
    val clientSecret: String? = null,
    val scope: Set<String>? = null,
    val authorizedGrantTypes: Set<String>? = null,
    val registeredRedirectUri: Set<String>? = null,
    val accessTokenValiditySeconds: Int? = null,
    val refreshTokenValiditySeconds: Int? = null,
    val grantedAuthorities: Collection<GrantedAuthority>? = null
)