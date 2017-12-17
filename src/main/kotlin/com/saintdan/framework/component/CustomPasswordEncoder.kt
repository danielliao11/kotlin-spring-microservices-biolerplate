package com.saintdan.framework.component

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * Custom encrypt utilities.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
@Component
class CustomPasswordEncoder : PasswordEncoder {

  /**
   * Encode the password.
   *
   * @param rawPassword raw password
   * @return encoded password
   */
  override fun encode(rawPassword: CharSequence): String {
    val rawPwd = rawPassword as String
    return BCrypt.hashpw(rawPwd, BCrypt.gensalt())
  }

  /**
   * Matches raw password and encoded password.
   *
   * @param rawPassword     raw password
   * @param encodedPassword encoded password
   * @return match or not
   */
  override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
    val rawPwd = rawPassword as String
    return BCrypt.checkpw(rawPwd, encodedPassword)
  }
}