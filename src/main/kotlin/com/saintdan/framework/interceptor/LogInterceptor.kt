package com.saintdan.framework.interceptor

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 20/12/2017
 * @since JDK1.8
 */
@Component
class LogInterceptor (private val logHelper: LogHelper) : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
    return true
  }

  override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?,
                          modelAndView: ModelAndView?) {
  }

  override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?,
                               handler: Any?, ex: Exception?) {
    val method = request!!.method
    if (!HttpMethod.GET.matches(method)) {
      val path = request.requestURI
      val status = HttpStatus.resolve(response!!.status)
      if (!path.contains(ResourcePath.OPEN) && !status!!.isError) {
        logHelper.log(HttpMethod.resolve(method)!!, path)
      }
    }
  }
}