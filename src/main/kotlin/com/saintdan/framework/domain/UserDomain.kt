package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.param.UserParam
import com.saintdan.framework.po.Role
import com.saintdan.framework.po.User
import com.saintdan.framework.repo.RoleRepository
import com.saintdan.framework.repo.UserRepository
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
class UserDomain(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository) {

  @Transactional
  @Throws(ElementAlreadyExistsException::class)
  fun create(param: UserParam): User =
      usrExists(param.usr!!)
          .let { param2Po(param) }
          .let { userRepository.save(it) }

  fun all(): MutableList<User> = userRepository.findAll()

  fun findById(id: Long): User? = userRepository.findById(id).orElse(null)

  @Transactional
  @Throws(EntityNotFoundException::class, JpaObjectRetrievalFailureException::class)
  fun update(id: Long, param: UserParam): User =
      userRepository.getOne(id)
          .let { param2Po(param, it) }
          .let { userRepository.save(it) }

  @Transactional
  @Throws(EntityNotFoundException::class, JpaObjectRetrievalFailureException::class)
  fun updateRoles(id: Long, param: UserParam): User {
    val user = userRepository.getOne(id)
    user.roles = getRoles(param.roleIds!!)
    return userRepository.save(user)
  }

  @Transactional
  @Throws(EmptyResultDataAccessException::class)
  fun deepDelete(id: Long) = userRepository.deleteById(id)

  private fun param2Po(param: UserParam): User =
      User(
          usr = param.usr!!,
          pwd = param.pwd!!,
          nickname = param.nickname ?: "",
          description = param.description ?: ""
      )

  private fun param2Po(param: UserParam, user: User): User =
      user.copy(
          nickname = ObjectUtils.parse(param.nickname, user.nickname),
          description = ObjectUtils.parse(param.description, user.description)
      )

  private fun getRoles(ids: Set<Long>): Set<Role> = roleRepository.findAllById(ids).toMutableSet()

  @Throws(ElementAlreadyExistsException::class)
  private fun usrExists(usr: String) {
    userRepository.findByUsr(usr)
        .ifPresent { throw ElementAlreadyExistsException("usr already exists") }
  }
}