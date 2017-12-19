package com.saintdan.framework.tool

import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
object LogUtils {
  fun trackInfo(logger: Logger, msg: String) {
    logger.info(generateTraceString(msg, null))
  }

  fun trackWarn(logger: Logger, msg: String) {
    logger.warn(generateStackTrace(msg))
  }

  fun traceDebug(logger: Logger, e: Throwable) {
    logger.debug(generateTraceString(null, e))
  }

  fun traceDebug(logger: Logger, e: Throwable, errorMsg: String) {
    logger.debug(generateTraceString(errorMsg, e))
  }

  fun traceError(logger: Logger, errorMsg: String) {
    traceError(logger, null, errorMsg)
  }

  fun traceError(logger: Logger, e: Throwable) {
    traceError(logger, e, null)
  }

  fun traceError(logger: Logger, e: Throwable?, errorMsg: String?) {
    logger.error(generateTraceString(errorMsg, e))
  }

  /**
   * Generate stack trace.
   *
   * @param msg message
   * @return stack trace
   */
  fun generateStackTrace(msg: String): String {
    val sb = StringBuffer()
    sb.append(msg)
    val trace = Thread.currentThread().stackTrace
    for (i in 3 until trace.size) {
      sb.append("\tat ")
      sb.append(trace[i]).append("\n")
    }
    return sb.toString()
  }

  /**
   * Generate trace string.
   *
   * @param errorMsg error message
   * @param e        exception
   * @return trace string
   */
  fun generateTraceString(errorMsg: String?, e: Throwable?): String {
    val w = StringWriter()
    w.append("Message is: ").append(errorMsg)
    val out = PrintWriter(w)
    if (!StringUtils.isEmpty(errorMsg)) {
      out.println(errorMsg)
    }
    e?.printStackTrace(out)
    return w.toString()
  }
}