package com.saintdan.framework.filter

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * CORS filter
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 13/12/2017
 * @since JDK1.8
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SimpleCORSFilter : Filter {

  override fun init(filterConfig: FilterConfig) {}

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
    val response = res as HttpServletResponse
    val request = req as HttpServletRequest
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
    response.setHeader("Access-Control-Max-Age", "3600")
    response
        .setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type")

    if ("OPTIONS".equals(request.method, ignoreCase = true)) {
      response.status = HttpServletResponse.SC_OK
    } else {
      chain.doFilter(req, res)
    }
  }

  override fun destroy() {}
}