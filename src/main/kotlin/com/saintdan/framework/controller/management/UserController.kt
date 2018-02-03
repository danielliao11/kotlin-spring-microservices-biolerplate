package com.saintdan.framework.controller.management

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.domain.UserDomain
import com.saintdan.framework.enums.ErrorType
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.param.UserParam
import com.saintdan.framework.po.User
import com.saintdan.framework.vo.ErrorVO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.USERS)
class UserController(
    private val userDomain: UserDomain,
    private val logHelper: LogHelper) {

  @PostMapping
  @ApiOperation(value = "Create user", response = User::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun create(@RequestBody param: UserParam): ResponseEntity<Any> =
      try {
        userDomain.create(param).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
      } catch (e: ElementAlreadyExistsException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = e.code,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.USER.uri())
      }

  @GetMapping
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun all(): ResponseEntity<MutableList<User>> = userDomain.all().let { ResponseEntity.ok(it) }

  @GetMapping("{id}")
  @ApiOperation(value = "Detail of user", response = User::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun detail(@PathVariable id: Long): ResponseEntity<Any> = userDomain.findById(id).let { if (it == null) ResponseEntity.ok().build() else ResponseEntity.ok(it) }

  @PutMapping("{id}")
  @ApiOperation(value = "Update user", response = User::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun update(@RequestBody param: UserParam, @PathVariable id: Long): ResponseEntity<Any> =
      try {
        userDomain.update(id, param).let { ResponseEntity.ok(it) }
      } catch (e: EntityNotFoundException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = ErrorType.SYS0004.name,
            error_description = e.localizedMessage
        ))
      } catch (e: JpaObjectRetrievalFailureException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = ErrorType.SYS0004.name,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.USER.uri())
      }

  @PatchMapping("{id}")
  @ApiOperation(value = "Update users' roles", response = User::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun updateRoles(@RequestBody param: UserParam, @PathVariable id: Long): ResponseEntity<Any> =
      try {
        userDomain.updateRoles(id, param).let { ResponseEntity.ok(it) }
      } catch (e: EntityNotFoundException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = ErrorType.SYS0004.name,
            error_description = e.localizedMessage
        ))
      } catch (e: JpaObjectRetrievalFailureException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = ErrorType.SYS0004.name,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.USER.uri())
      }

  @DeleteMapping("{id}")
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun delete(@PathVariable id: Long): ResponseEntity<Any> =
      try {
        userDomain.deepDelete(id).let { ResponseEntity.noContent().build() }
      } catch (e: EmptyResultDataAccessException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = ErrorType.SYS0004.name,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.DELETE, ResourceUri.USER.uri())
      }

  private val logger = LoggerFactory.getLogger(UserController::class.java)
}