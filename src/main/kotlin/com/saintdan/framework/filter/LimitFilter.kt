package com.saintdan.framework.filter

import com.saintdan.framework.servlet.RequestWrapper
import com.saintdan.framework.tool.LogUtils
import com.saintdan.framework.tool.RemoteAddressUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Limit filter.
 * <pre>
 *   I use map as an cache in this case.
 *   You can also use redis.
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
class LimitFilter(private val env: Environment) : Filter {

  private val map = ConcurrentHashMap<String, RequestCount>()

  @Throws(ServletException::class)
  override fun init(filterConfig: FilterConfig) {
    LogUtils.trackInfo(logger, "Initiating LimitFilter")
  }

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
    if (request is HttpServletRequest) {
      val req = RequestWrapper(request)
      val LIMIT_KEY = "Limit-Key"
      val range = env.getProperty("request.range") ?: "10000"
      val count = env.getProperty("request.count") ?: "3"
      val limitKey = req.getHeader(LIMIT_KEY)
      if (StringUtils.isNotBlank(limitKey) && !limit(RequestLimit(RemoteAddressUtils.getRealIp(req),
          req.requestURI,
          limitKey,
          range.toLong(),
          count.toInt()))) {
        (response as HttpServletResponse).status = HttpStatus.TOO_MANY_REQUESTS.value()
        return
      }
      chain.doFilter(req, response)
    } else {
      (response as HttpServletResponse).status = HttpStatus.BAD_REQUEST.value()
    }
  }

  override fun destroy() {
    LogUtils.trackInfo(logger, "Destroying LimitFilter")
  }

  private fun limit(requestLimit: RequestLimit): Boolean {
    val key = arrayOf(requestLimit.ip, requestLimit.path,
        requestLimit.limitKey).joinToString("_")
    if (!map.containsKey(key)) {
      map.put(key, RequestCount(
          key = key,
          count = 1
      ))
    } else {
      val requestCount = map.get(key)
      val frequency = System.currentTimeMillis() - requestCount!!.firstReqAt
      if (frequency > requestLimit.range) {
        map.remove(key)
      } else {
        if (requestCount.count >= requestLimit.count && frequency <= requestLimit
            .range) {
          return false
        } else {
          requestCount.count += 1
          map.put(key, requestCount)
        }
      }
    }
    return true
  }

  data class RequestLimit(
      val ip: String,
      val path: String,
      val limitKey: String,
      val range: Long,
      val count: Int
  )

  data class RequestCount(
      val key: String,
      var count: Int,
      val firstReqAt: Long = System.currentTimeMillis()
  )

  companion object {
    private val logger = LoggerFactory.getLogger(LimitFilter::class.java)
  }
}