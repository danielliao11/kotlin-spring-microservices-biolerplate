package com.saintdan.framework.config

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/12/2017
 * @since JDK1.8
 */
@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

  override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
    argumentResolvers!!.add(SpecificationArgumentResolver())
  }
}