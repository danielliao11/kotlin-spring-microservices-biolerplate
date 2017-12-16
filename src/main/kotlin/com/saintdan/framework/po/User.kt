package com.saintdan.framework.po

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

/**
 * Authorized users, provide for spring security oauth2.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.roles", attributeNodes = [NamedAttributeNode("roles")])
data class User(

    @GenericGenerator(name = "userSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          (Parameter(name = "sequence_name", value = "users_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "userSequenceGenerator")
    @Column(updatable = false)
    val id: Long,

    @Column(length = 50)
    val nickname: String,

    @Column(nullable = false, length = 20)
    val usr: String,

    @Column(nullable = false, length = 200)
    val pwd: String,

    @Column(nullable = false)
    val isAccountNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    val isAccountNonLockedAlias: Boolean = true,

    @Column(nullable = false)
    val isCredentialsNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    val isEnabledAlias: Boolean = true,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    // Last login time
    val lastLoginAt: Long = System.currentTimeMillis(),

    // Last login IP address
    val ip: String? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    val createdBy: Long = 0,

    @Column(nullable = false)
    val lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    val lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    val version: Int = 0,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [(CascadeType.REFRESH)])
    @JoinTable(name = "users_has_roles", joinColumns = [(JoinColumn(name = "user_id"))], inverseJoinColumns = [(JoinColumn(name = "role_id"))])
    val roles: Set<Role> = emptySet(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [(CascadeType.REMOVE)])
    val accounts: Set<Account> = emptySet()
) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getUsername(): String {
    return usr
  }

  override fun getPassword(): String {
    return pwd
  }

  override fun isEnabled(): Boolean {
    return isEnabledAlias
  }

  override fun isCredentialsNonExpired(): Boolean {
    return isCredentialsNonExpiredAlias
  }

  override fun isAccountNonExpired(): Boolean {
    return isAccountNonExpiredAlias
  }

  override fun isAccountNonLocked(): Boolean {
    return isAccountNonLockedAlias
  }
}