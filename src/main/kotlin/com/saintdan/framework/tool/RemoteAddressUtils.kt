package com.saintdan.framework.tool

import com.saintdan.framework.servlet.RequestWrapper
import org.apache.commons.lang3.StringUtils
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
class RemoteAddressUtils {
  companion object {
    fun getRealIp(request: HttpServletRequest): String {
      var ip = request.getHeader("X-Forwarded-For")
      if (StringUtils.isNotEmpty(ip) && !"unKnown".equals(ip, ignoreCase = true)) {
        val index = ip.indexOf(",")
        return if (index != -1) {
          ip.substring(0, index)
        } else {
          ip
        }
      }
      ip = request.getHeader("X-Real-IP")
      return if (StringUtils.isNotEmpty(ip) && !"unKnown".equals(ip, ignoreCase = true)) {
        ip
      } else request.remoteAddr
    }

    fun getRealIp(request: RequestWrapper): String {
      var ip = request.getHeader("X-Forwarded-For")
      if (StringUtils.isNotEmpty(ip) && !"unKnown".equals(ip, ignoreCase = true)) {
        val index = ip.indexOf(",")
        return if (index != -1) {
          ip.substring(0, index)
        } else {
          ip
        }
      }
      ip = request.getHeader("X-Real-IP")
      return if (StringUtils.isNotEmpty(ip) && !"unKnown".equals(ip, ignoreCase = true)) {
        ip
      } else request.remoteAddr
    }
  }
}