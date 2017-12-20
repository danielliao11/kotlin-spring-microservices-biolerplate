package com.saintdan.framework.config

import com.saintdan.framework.interceptor.LogInterceptor
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/12/2017
 * @since JDK1.8
 */
@Configuration
class WebMvcConfiguration(private val logInterceptor: LogInterceptor) : WebMvcConfigurer {

  override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
    argumentResolvers!!.add(SpecificationArgumentResolver())
  }

  override fun addInterceptors(registry: InterceptorRegistry?) {
    registry!!.addInterceptor(logInterceptor)
  }
}