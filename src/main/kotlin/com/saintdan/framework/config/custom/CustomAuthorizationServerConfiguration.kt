package com.saintdan.framework.config.custom

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@Configuration
@EnableAuthorizationServer
class CustomAuthorizationServerConfiguration(
    private val dataSource: DataSource,
    private val clientDetailsService: CustomClientDetailsService,
    private val userDetailsService: CustomUserDetailsService) : AuthorizationServerConfigurerAdapter() {

  override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
    endpoints!!
        .tokenStore(tokenStore())
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
  }

  @Throws(Exception::class)
  override fun configure(clients: ClientDetailsServiceConfigurer?) {
    // Use JDBC client.
    clients!!.withClientDetails(clientDetailsService)
  }

  // Token store type.
  @Bean
  fun tokenStore(): JdbcTokenStore {
    return JdbcTokenStore(dataSource)
  }

  private val authenticationManager: AuthenticationManager = OAuth2AuthenticationManager()
}