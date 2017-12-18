package com.saintdan.framework.controller.management

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.domain.UserDomain
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.UserParam
import com.saintdan.framework.po.User
import com.saintdan.framework.vo.ErrorVO
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.USERS)
class UserController(
    private val userDomain: UserDomain,
    private val logHelper: LogHelper) {

  @PostMapping("test")
  fun test(principal: Principal) {
    if (principal is Authentication) {
      val clientId = principal.name
    }
  }

  @PostMapping
  @ApiOperation(value = "Create user", response = User::class)
  fun create(@RequestBody param: UserParam): ResponseEntity<Any> {
    return try {
      userDomain.create(param)
          .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    } catch (e: ElementAlreadyExistsException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.USER.name)
    }
  }

  @GetMapping
  fun all(): ResponseEntity<MutableList<User>> {
    return userDomain.all()
        .let { ResponseEntity.ok(it) }
  }

  @GetMapping("{id}")
  @ApiOperation(value = "Detail of user", response = User::class)
  fun detail(@PathVariable id: Long): ResponseEntity<Any> {
    return userDomain.findById(id)
        .let { if (it == null) ResponseEntity.ok().build() else ResponseEntity.ok(it) }
  }
  
  @PutMapping("{id}")
  @ApiOperation(value = "Update user", response = User::class)
  fun update(@RequestBody param: UserParam, @PathVariable id: Long): ResponseEntity<Any> {
    return try {
      userDomain.update(param)
          .let { ResponseEntity.ok(it) }
    } catch (e: NoSuchElementByIdException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.USER.name)
    }
  }
  
  @DeleteMapping("{id}")
  fun delete(@PathVariable id: Long): ResponseEntity<Any> {
    return try {
      userDomain.deepDelete(id)
          .let { ResponseEntity.noContent().build() }
    } catch (e: NoSuchElementByIdException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.DELETE, ResourceUri.USER.name)
    }
  }

  private val logger = LoggerFactory.getLogger(UserController::class.java)
}