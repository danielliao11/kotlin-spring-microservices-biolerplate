package com.saintdan.framework.domain

import com.saintdan.framework.exception.ElementAlreadyExistsException
import com.saintdan.framework.exception.NoSuchElementByIdException
import com.saintdan.framework.param.UserParam
import com.saintdan.framework.po.User
import com.saintdan.framework.repo.RoleRepository
import com.saintdan.framework.repo.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
  fun create(param: UserParam): User {
    usrExists(param.usr!!)
    return param2PO(param)
        .let { userRepository.save(it) }
  }

  fun all(): MutableList<User> {
    return userRepository.findAll()
  }

  fun findById(id: Long): User? {
    return userRepository.findById(id).orElse(null)
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun update(param: UserParam): User {
    val user = userRepository.findById(param.id!!).orElseThrow { NoSuchElementException() }
    return user
        .let { param2PO(param, it) }
        .let { userRepository.save(it) }
  }

  @Transactional
  @Throws(NoSuchElementByIdException::class)
  fun deepDelete(id: Long) {
    val user = userRepository.findById(id).orElseThrow { NoSuchElementException() }
    userRepository.delete(user)
  }

  private fun param2PO(param: UserParam): User {
    val roles = if (param.roleIds != null) roleRepository.findAllById(param.roleIds!!) else emptyList()
    return User(
        usr = param.usr!!,
        pwd = param.pwd!!,
        nickname = param.nickname ?: "",
        description = param.description ?: "",
        roles = roles.toMutableSet()
    )
  }

  private fun param2PO(param: UserParam, user: User): User {
    val roles = if (param.roleIds != null) roleRepository.findAllById(param.roleIds!!) else emptyList()
    return user.copy(
        nickname = param.nickname ?: user.nickname,
        description = param.description ?: user.description,
        roles = roles.toMutableSet()
    )
  }

  @Throws(ElementAlreadyExistsException::class)
  private fun usrExists(usr: String) {
    userRepository.findByUsr(usr)
        .ifPresent { throw ElementAlreadyExistsException("usr already exists") }
  }
}