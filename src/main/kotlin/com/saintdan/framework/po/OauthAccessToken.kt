package com.saintdan.framework.po

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Oauth access token.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "oauth_access_token")
data class OauthAccessToken(
    @Id private val tokenId: String,
    val userName: String,
    val clientId: String
)