package com.saintdan.framework.repo

import com.saintdan.framework.po.OauthAccessToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
interface OauthAccessTokenRepository : JpaRepository<OauthAccessToken, String> {

  fun findByUserName(userName: String): Optional<OauthAccessToken>
}