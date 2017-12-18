package com.saintdan.framework.config

import com.saintdan.framework.constant.ResourcePath
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 12/12/2017
 * @since JDK1.8
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig {

  /**
   * Resource of api
   *
   * @return [ResourceServerConfiguration]
   */
  @Bean protected fun managementResources(): ResourceServerConfiguration {

    val resource = object : ResourceServerConfiguration() {}

    resource.setConfigurers(listOf<ResourceServerConfigurer>(object : ResourceServerConfigurerAdapter() {

      @Throws(Exception::class)
      override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.resourceId("api")
      }

      @Throws(Exception::class)
      override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(getUrl(ResourcePath.OPEN)).permitAll()
            .antMatchers(getUrl(ResourcePath.MANAGEMENT)).hasAnyAuthority("root", "management")
            .antMatchers(getUrl(ResourcePath.APP)).hasAnyAuthority("root", "management", "app")
      }
    }))

    resource.order = 1

    return resource
  }

  fun getUrl(charSequence: CharSequence): String {
    return StringBuilder(ResourcePath.FIX)
        .append(ResourcePath.API).append(ResourcePath.V1).append(charSequence)
        .append(ResourcePath.FIX).toString()
  }
}