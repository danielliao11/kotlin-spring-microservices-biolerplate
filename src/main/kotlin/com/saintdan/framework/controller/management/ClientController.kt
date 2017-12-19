package com.saintdan.framework.controller.management

import com.saintdan.framework.component.LogHelper
import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.domain.ClientDomain
import com.saintdan.framework.enums.ResourceUri
import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.ClientParam
import com.saintdan.framework.po.Client
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
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.CLIENTS)
class ClientController(
    private val clientDomain: ClientDomain,
    private val logHelper: LogHelper) {

  @PostMapping
  @ApiOperation(value = "Create client", response = Client::class)
  fun create(@RequestBody param: ClientParam): ResponseEntity<Any> {
    return try {
      clientDomain.create(param)
          .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    } catch (e: ElementAlreadyExistsException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.POST, ResourceUri.RESOURCE.name)
    }
  }

  @GetMapping
  fun all(): ResponseEntity<MutableList<Client>> {
    return clientDomain.all()
        .let { ResponseEntity.ok(it) }
  }

  @GetMapping("{id}")
  @ApiOperation(value = "Detail of user", response = Client::class)
  fun detail(@PathVariable id: Long): ResponseEntity<Any> {
    return clientDomain.findById(id)
        .let { if (it == null) ResponseEntity.ok().build() else ResponseEntity.ok(it) }
  }
  
  @PutMapping("{id}")
  @ApiOperation(value = "Update user", response = Client::class)
  fun update(@RequestBody param: ClientParam, @PathVariable id: Long): ResponseEntity<Any> {
    return try {
      clientDomain.update(param)
          .let { ResponseEntity.ok(it) }
    } catch (e: NoSuchElementByIdException) {
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorVO(
          error = e.code,
          error_description = e.localizedMessage
      ))
    } catch (e: Exception) {
      logHelper.log(HttpStatus.INTERNAL_SERVER_ERROR, logger, e, HttpMethod.PUT, ResourceUri.RESOURCE.name)
    }
  }

  private val logger = LoggerFactory.getLogger(ClientController::class.java)
}