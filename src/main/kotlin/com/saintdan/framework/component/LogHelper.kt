package com.saintdan.framework.component

import com.saintdan.framework.domain.ErrorLogDomain
import com.saintdan.framework.domain.LogDomain
import com.saintdan.framework.enums.ErrorType
import com.saintdan.framework.po.ErrorLog
import com.saintdan.framework.po.Log
import com.saintdan.framework.tool.LogUtils
import com.saintdan.framework.tool.RemoteAddressUtils
import com.saintdan.framework.tool.SpringSecurityUtils
import com.saintdan.framework.vo.ErrorVO
import org.slf4j.Logger
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Component
class LogHelper(private val logDomain: LogDomain,
                private val errorLogDomain: ErrorLogDomain) {

  fun log(request: HttpServletRequest) {
    logDomain.create(Log(
        usr = SpringSecurityUtils.getCurrentUsername(),
        ip = RemoteAddressUtils.getRealIp(request),
        clientId = SpringSecurityUtils.getCurrentClientId(),
        path = request.requestURI.substring(request.contextPath.length),
        method = request.method
    ))
  }

  fun log(method: HttpMethod, usr: String, ip: String, clientId: String, path: String) {
    logDomain.create(Log(
        usr = usr,
        ip = ip,
        clientId = SpringSecurityUtils.getCurrentClientId(),
        path = path,
        method = method.name
    ))
  }

  fun log(method: HttpMethod) {
    logDomain.create(Log(
        usr = SpringSecurityUtils.getCurrentUsername(),
        ip = SpringSecurityUtils.getCurrentUserIp(),
        clientId = SpringSecurityUtils.getCurrentClientId(),
        method = method.name
    ))
  }

  fun log(method: HttpMethod, path: String) {
    logDomain.create(Log(
        usr = SpringSecurityUtils.getCurrentUsername(),
        ip = SpringSecurityUtils.getCurrentUserIp(),
        clientId = SpringSecurityUtils.getCurrentClientId(),
        path = path,
        method = method.name
    ))
  }

  fun log(name: String, method: HttpMethod, path: String) {
    errorLogDomain.create(ErrorLog(
        name = name,
        usr = SpringSecurityUtils.getCurrentUsername(),
        ip = SpringSecurityUtils.getCurrentUserIp(),
        clientId = SpringSecurityUtils.getCurrentClientId(),
        path = path,
        method = method.name
    ))
  }

  fun log(status: HttpStatus, logger: Logger, e: Throwable, method: HttpMethod, path: String): ResponseEntity<Any> {
    LogUtils.traceError(logger, e)
    log(e.javaClass.name, method, path)
    return ResponseEntity.status(status).body(ErrorVO(
        error = ErrorType.UNKNOWN.name,
        error_description = e.localizedMessage ?: ""
    ))
  }
}