package com.saintdan.framework.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.saintdan.framework.annotation.NotNullField
import com.saintdan.framework.annotation.SizeField
import com.saintdan.framework.enums.ErrorType
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.servlet.RequestWrapper
import com.saintdan.framework.vo.ErrorVO
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Validate helper.
 * <pre>
 *   Validate param bean.
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
@Component
@Order(1)
@WebFilter(filterName = "ValidateFilter")
class ValidateFilter : Filter {

  override fun init(filterConfig: FilterConfig) {}

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
    if (request is HttpServletRequest) {
      val req = RequestWrapper(request)
      val stringBuilder = StringBuilder()
      val method = req.method
      if (HttpMethod.POST.matches(method) || HttpMethod.PUT.matches(method) || HttpMethod.PATCH.matches(method)) {
        req.reader.lines().forEach { stringBuilder.append(it) }
        val json = stringBuilder.toString()
        val mapper = ObjectMapper()
        val resourceUri = ResourceUri.resolve(req.requestURI)
        val resp = response as HttpServletResponse
        if (resourceUri.isUnknown) {
          resp.status = HttpStatus.NOT_FOUND.value()
          return
        }
        if (StringUtils.isBlank(json)) {
          resp.status = HttpStatus.BAD_REQUEST.value()
          return
        }
        val result = validate(mapper.readValue(json, resourceUri.clazz()), HttpMethod.resolve(method))
        if (StringUtils.isNotBlank(result)) {
          resp.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
          resp.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
          resp.writer.write(mapper.writeValueAsString(
              ErrorVO(
                  error = ErrorType.SYS0002.name,
                  error_description = result
                  )))
          return
        }
      }
      chain.doFilter(req, response)
    } else {
      (response as HttpServletResponse).status = HttpStatus.BAD_REQUEST.value()
    }
  }

  override fun destroy() {}

  private fun validate(param: Any, method: HttpMethod): String {
    val fields = param.javaClass.declaredFields
    for (field in fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField::class.java)) {
        continue // Ignore field without ParamField annotation.
      }
      field.isAccessible = true
      val notNullField = field.getAnnotation(NotNullField::class.java)
      try {
        if (Arrays.stream(notNullField.method)
            .anyMatch({ m -> m.matches(method.name) }) && (field.get(param) == null || StringUtils.isBlank(field.get(param).toString()))) {
          return notNullField.message
        }
      } catch (ignore: IllegalAccessException) {
      }

      if (field.isAnnotationPresent(SizeField::class.java)) {
        val size = field.getAnnotation(SizeField::class.java)
        try {
          if (ArrayUtils.contains(size.method, method)
              && (field.get(param).toString().length > size.max || field.get(param).toString().length < size.min)) {
            return notNullField.message
          }
        } catch (ignore: IllegalAccessException) {
        }

      }
    }
    return ""
  }
}