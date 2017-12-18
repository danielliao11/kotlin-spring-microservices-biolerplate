package com.saintdan.framework.controller.management

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.domain.RoleDomain
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.RoleParam
import com.saintdan.framework.po.Role
import com.saintdan.framework.vo.ErrorVO
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.ROLES)
class RoleController(
    private val roleDomain: RoleDomain,
    private val logHelper: LogHelper) {

  @PostMapping
  @ApiOperation(value = "Create role", response = Role::class)
  fun create(@RequestBody param: RoleParam): ResponseEntity<Any> {
    return try {
      roleDomain.create(param)
          .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    } catch (e: ElementAlreadyExistsException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.ROLE.name)
    }
  }

  @GetMapping
  fun all(): ResponseEntity<MutableList<Role>> {
    return roleDomain.all()
        .let { ResponseEntity.ok(it) }
  }

  @GetMapping("{id}")
  @ApiOperation(value = "Detail of role", response = Role::class)
  fun detail(@PathVariable id: Long): ResponseEntity<Any> {
    return roleDomain.findById(id)
        .let { if (it == null) ResponseEntity.ok().build() else ResponseEntity.ok(it) }
  }
  
  @PutMapping("{id}")
  @ApiOperation(value = "Update role", response = Role::class)
  fun update(@RequestBody param: RoleParam, @PathVariable id: Long): ResponseEntity<Any> {
    return try {
      roleDomain.update(param)
          .let { ResponseEntity.ok(it) }
    } catch (e: NoSuchElementByIdException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.ROLE.name)
    }
  }
  
  @DeleteMapping("{id}")
  fun delete(@PathVariable id: Long): ResponseEntity<Any> {
    return try {
      roleDomain.deepDelete(id)
          .let { ResponseEntity.noContent().build() }
    } catch (e: NoSuchElementByIdException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.DELETE, ResourceUri.ROLE.name)
    }
  }

  private val logger = LoggerFactory.getLogger(ResourceController::class.java)
}