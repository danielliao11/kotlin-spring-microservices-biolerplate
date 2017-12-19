package com.saintdan.framework.config.custom

import com.saintdan.framework.repo.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

  @Throws(UsernameNotFoundException::class)
  override fun loadUserByUsername(usr: String): UserDetails {
    return userRepository.findByUsr(usr).orElseThrow(
        // Throw cannot find any user by this usr param.
        { UsernameNotFoundException(String.format("User %s does not exist!", usr)) })
  }
}