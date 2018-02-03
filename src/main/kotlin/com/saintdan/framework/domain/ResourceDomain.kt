package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.param.ResourceParam
import com.saintdan.framework.po.Resource
import com.saintdan.framework.repo.ResourceRepository
import com.saintdan.framework.tool.ObjectUtils
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

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
  fun create(param: ResourceParam): Resource =
      nameExists(param.name!!)
          .let { param2PO(param) }
          .let { resourceRepository.save(it) }

  fun all(): MutableList<Resource> = resourceRepository.findAll()

  fun findById(id: Long): Resource? = resourceRepository.findById(id).orElse(null)

  @Transactional
  @Throws(EntityNotFoundException::class, JpaObjectRetrievalFailureException::class)
  fun update(id: Long, param: ResourceParam): Resource =
      resourceRepository.getOne(id)
          .let { param2PO(param, it) }
          .let { resourceRepository.save(it) }

  @Transactional
  @Throws(EmptyResultDataAccessException::class)
  fun deepDelete(id: Long) = resourceRepository.deleteById(id)

  private fun param2PO(param: ResourceParam): Resource {
    return Resource(
        name = param.name ?: "",
        description = param.description ?: ""
    )
  }

  private fun param2PO(param: ResourceParam, resource: Resource): Resource {
    return resource.copy(
        name = ObjectUtils.parse(param.name, resource.name),
        description = ObjectUtils.parse(param.description, resource.description)
    )
  }

  @Throws(ElementAlreadyExistsException::class)
  private fun nameExists(name: String) {
    resourceRepository.findByName(name)
        .ifPresent { throw ElementAlreadyExistsException("name already exists") }
  }
}