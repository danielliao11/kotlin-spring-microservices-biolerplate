package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.param.RoleParam
import com.saintdan.framework.po.Resource
import com.saintdan.framework.po.Role
import com.saintdan.framework.repo.ResourceRepository
import com.saintdan.framework.repo.RoleRepository
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
class RoleDomain(
    private val roleRepository: RoleRepository,
    private val resourceRepository: ResourceRepository) {

  @Transactional
  @Throws(ElementAlreadyExistsException::class)
  fun create(param: RoleParam): Role =
      nameExists(param.name!!)
          .let { param2Po(param) }
          .let { roleRepository.save(it) }

  fun all(): MutableList<Role> = roleRepository.findAll()

  fun findById(id: Long): Role? = roleRepository.findById(id).orElse(null)

  @Transactional
  @Throws(EntityNotFoundException::class, JpaObjectRetrievalFailureException::class)
  fun update(id: Long, param: RoleParam): Role =
      roleRepository.getOne(id)
          .let { param2Po(param, it) }
          .let { roleRepository.save(it) }

  @Transactional
  @Throws(EntityNotFoundException::class, JpaObjectRetrievalFailureException::class)
  fun updateResources(id: Long, param: RoleParam): Role {
    val role = roleRepository.getOne(id)
    role.resources = getResources(param.resourceIds!!)
    return roleRepository.save(role)
  }

  @Transactional
  @Throws(EmptyResultDataAccessException::class)
  fun deepDelete(id: Long) = roleRepository.deleteById(id)

  private fun param2Po(param: RoleParam): Role =
      Role(
          name = param.name ?: "",
          description = param.description ?: ""
      )

  private fun param2Po(param: RoleParam, role: Role): Role =
      role.copy(
          name = ObjectUtils.parse(param.name, role.name),
          description = ObjectUtils.parse(param.description, role.description)
      )

  private fun getResources(ids: Set<Long>): Set<Resource> = resourceRepository.findAllById(ids).toMutableSet()

  @Throws(ElementAlreadyExistsException::class)
  private fun nameExists(name: String) {
    roleRepository.findByName(name)
        .ifPresent { throw ElementAlreadyExistsException("name already exists") }
  }
}