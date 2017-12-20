package com.saintdan.framework.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Swagger configuration.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 04/12/2017
 * @since JDK1.8
 */
@Configuration
@EnableSwagger2
class SwaggerConfiguration {

  @Bean fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.saintdan.framework.controller"))
      .paths(PathSelectors.any())
      .build()
}