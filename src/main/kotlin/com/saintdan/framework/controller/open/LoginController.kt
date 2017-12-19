package com.saintdan.framework.controller.open

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.param.LoginParam
import com.saintdan.framework.service.LoginService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.OPEN + ResourcePath.LOGIN)
class LoginController(
    private val loginService: LoginService,
    private val logHelper: LogHelper) {

  @PostMapping
  @ApiOperation(value = "Login", httpMethod = "POST", response = OAuth2AccessToken::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
      ApiImplicitParam(name = "Limit-Key", value = "limit key", paramType = "header", dataType = "string"))
  fun login(request: HttpServletRequest, @RequestBody param: LoginParam): ResponseEntity<*> {
    return try {
      loginService.login(param, request)
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.LOGIN.name)
    }
  }

  private val logger = LoggerFactory.getLogger(LoginController::class.java)
}