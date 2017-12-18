package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.ResourceParam
import com.saintdan.framework.po.Resource
import com.saintdan.framework.repo.ResourceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
class ResourceDomain(private val resourceRepository: ResourceRepository) {

  @Transactional
  @Throws(ElementAlreadyExistsException::class)
  fun create(param: ResourceParam): Resource {
    nameExists(param.name!!)
    return param2PO(param)
        .let { resourceRepository.save(it) }
  }

  fun all(): MutableList<Resource> {
    return resourceRepository.findAll()
  }

  fun findById(id: Long): Resource? {
    return resourceRepository.findById(id).orElse(null)
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun update(param: ResourceParam): Resource {
    val resource = resourceRepository.findById(param.id!!) ?: throw NoSuchElementException()
    return resource
        .let { param2PO(param, it.get()) }
        .let { resourceRepository.save(it) }
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun deepDelete(id: Long) {
    val resource = findById(id) ?: throw NoSuchElementException()
    resourceRepository.delete(resource)
  }

  private fun param2PO(param: ResourceParam): Resource {
    return Resource(
        name = param.name ?: "",
        description = param.description ?: ""
    )
  }

  private fun param2PO(param: ResourceParam, resource: Resource): Resource {
    return resource.copy(
        name = param.name ?: resource.name,
        description = param.description ?: resource.description
    )
  }

  @Throws(ElementAlreadyExistsException::class)
  private fun nameExists(name: String) {
    resourceRepository.findByName(name)
        .ifPresent { throw ElementAlreadyExistsException("name already exists") }
  }
}