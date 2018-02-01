package com.saintdan.framework.controller.management

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.domain.ResourceDomain
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.ResourceParam
import com.saintdan.framework.po.Resource
import com.saintdan.framework.vo.ErrorVO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
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
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.RESOURCES)
class ResourceController(
    private val resourceDomain: ResourceDomain,
    private val logHelper: LogHelper) {

  @PostMapping
  @ApiOperation(value = "Create resource", response = Resource::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun create(@RequestBody param: ResourceParam): ResponseEntity<Any> =
      try {
        resourceDomain.create(param).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
      } catch (e: ElementAlreadyExistsException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = e.code,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.RESOURCE.uri())
      }

  @GetMapping
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun all(): ResponseEntity<MutableList<Resource>> = resourceDomain.all().let { ResponseEntity.ok(it) }

  @GetMapping("{id}")
  @ApiOperation(value = "Detail of resource", response = Resource::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun detail(@PathVariable id: Long): ResponseEntity<Any> =
    resourceDomain.findById(id).let { if (it == null) ResponseEntity.ok().build() else ResponseEntity.ok(it) }

  @PutMapping("{id}")
  @ApiOperation(value = "Update resource", response = Resource::class)
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun update(@RequestBody param: ResourceParam, @PathVariable id: Long): ResponseEntity<Any> =
      try {
        resourceDomain.update(param).let { ResponseEntity.ok(it) }
      } catch (e: NoSuchElementByIdException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = e.code,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.RESOURCE.uri())
      }

  @DeleteMapping("{id}")
  @ApiImplicitParams(
      ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  )
  fun delete(@PathVariable id: Long): ResponseEntity<Any> =
      try {
        resourceDomain.deepDelete(id).let { ResponseEntity.noContent().build() }
      } catch (e: NoSuchElementByIdException) {
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
            error = e.code,
            error_description = e.localizedMessage
        ))
      } catch (e: Exception) {
        logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.DELETE, ResourceUri.RESOURCE.uri())
      }

  private val logger = LoggerFactory.getLogger(ResourceController::class.java)
}