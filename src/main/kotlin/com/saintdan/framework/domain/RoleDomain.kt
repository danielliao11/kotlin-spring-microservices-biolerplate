package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.RoleParam
import com.saintdan.framework.po.Role
import com.saintdan.framework.repo.ResourceRepository
import com.saintdan.framework.repo.RoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
class RoleDomain(private val roleRepository: RoleRepository,
                 private val resourceRepository: ResourceRepository) {

  @Transactional
  @Throws(ElementAlreadyExistsException::class)
  fun create(param: RoleParam): Role {
    nameExists(param.name!!)
    return param2PO(param)
        .let { roleRepository.save(it) }
  }

  fun all(): MutableList<Role> {
    return roleRepository.findAll()
  }

  fun findById(id: Long): Role? {
    return roleRepository.findById(id).orElse(null)
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun update(param: RoleParam): Role {
    val role = roleRepository.findById(param.id!!).orElseThrow { NoSuchElementException() }
    return param2PO(param, role)
        .let { roleRepository.save(it) }
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun deepDelete(id: Long) {
    val role = roleRepository.findById(id).orElseThrow { NoSuchElementException() }
    roleRepository.delete(role)
  }

  private fun param2PO(param: RoleParam): Role {
    val resources = if (param.resourceIds != null) resourceRepository.findAllById(param.resourceIds!!) else emptyList()
    return Role(
        name = param.name ?: "",
        description = param.description ?: "",
        resources = resources.toMutableSet()
    )
  }

  private fun param2PO(param: RoleParam, role: Role): Role {
    val resources = if (param.resourceIds != null) resourceRepository.findAllById(param.resourceIds!!) else emptyList()
    return role.copy(
        name = param.name ?: role.name,
        description = param.description ?: role.description,
        resources = if (param.resourceIds != null) resources.toMutableSet() else role.resources
    )
  }

  @Throws(ElementAlreadyExistsException::class)
  private fun nameExists(name: String) {
    roleRepository.findByName(name)
        .ifPresent { throw ElementAlreadyExistsException("nickname already exists") }
  }
}