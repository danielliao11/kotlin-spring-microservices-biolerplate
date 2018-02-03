package com.saintdan.framework.po

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saintdan.framework.listener.UpdateListener
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
@EntityListeners(UpdateListener::class)
data class User(

    @GenericGenerator(name = "userSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          Parameter(name = "sequence_name", value = "users_seq"),
          Parameter(name = "initial_value", value = "1"),
          Parameter(name = "increment_size", value = "1")])
    @Id
    @GeneratedValue(generator = "userSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(length = 50)
    val nickname: String = "",

    @Column(nullable = false, length = 20)
    val usr: String = "",

    @Column(nullable = false, length = 200)
    val pwd: String = "",

    @Column(nullable = false)
    @JsonIgnore
    val isAccountNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
    val isAccountNonLockedAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
    val isCredentialsNonExpiredAlias: Boolean = true,

    @Column(nullable = false)
    @JsonIgnore
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
    @JsonIgnore
    val createdBy: Long = 0,

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    @JsonIgnore
    var version: Int = 0,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REFRESH])
    @JoinTable(
        name = "users_has_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    @JsonIgnore
    var roles: Set<Role> = emptySet(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    var accounts: Set<Account> = emptySet()
) : UserDetails {
  companion object {
    private const val serialVersionUID = 6298843159549723556L
  }

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    val authorities = mutableListOf<GrantedAuthority>()
    roles
        .forEach { it.resources
            .forEach { authorities.add(SimpleGrantedAuthority(it.authority)) } }
    return authorities
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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val user = other as User?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, user!!.id)
        .append(isAccountNonExpiredAlias, user.isAccountNonExpiredAlias)
        .append(isAccountNonLockedAlias, user.isAccountNonLockedAlias)
        .append(isCredentialsNonExpiredAlias, user.isCredentialsNonExpiredAlias)
        .append(isEnabledAlias, user.isEnabledAlias)
        .append(lastLoginAt, user.lastLoginAt)
        .append(createdAt, user.createdAt)
        .append(createdBy, user.createdBy)
        .append(lastModifiedAt, user.lastModifiedAt)
        .append(lastModifiedBy, user.lastModifiedBy)
        .append(version, user.version)
        .append(nickname, user.nickname)
        .append(usr, user.usr)
        .append(pwd, user.pwd)
        .append(description, user.description)
        .append(ip, user.ip)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(nickname)
        .append(usr)
        .append(pwd)
        .append(isAccountNonExpiredAlias)
        .append(isAccountNonLockedAlias)
        .append(isCredentialsNonExpiredAlias)
        .append(isEnabledAlias)
        .append(description)
        .append(lastLoginAt)
        .append(ip)
        .append(createdAt)
        .append(createdBy)
        .append(lastModifiedAt)
        .append(lastModifiedBy)
        .append(version)
        .toHashCode()
  }

  override fun toString(): String =
      StringBuilder("User(")
          .append("id = ").append(id)
          .append(", nickname = ").append(nickname)
          .append(", usr = ").append(usr)
          .append(", pwd = ").append(pwd)
          .append(", description = ").append(description)
          .append(", lastLoginAt = ").append(lastLoginAt)
          .append(", ip = ").append(ip)
          .append(", isAccountNonExpiredAlias = ").append(isAccountNonExpiredAlias)
          .append(", isAccountNonLockedAlias = ").append(isAccountNonLockedAlias)
          .append(", isCredentialsNonExpiredAlias = ").append(isCredentialsNonExpiredAlias)
          .append(", isCredentialsNonExpiredAlias = ").append(isCredentialsNonExpiredAlias)
          .append(", isEnabledAlias = ").append(isEnabledAlias)
          .append(", createdAt = ").append(createdAt)
          .append(", createdBy = ").append(createdBy)
          .append(", lastModifiedAt = ").append(lastModifiedAt)
          .append(", lastModifiedBy = ").append(lastModifiedBy)
          .append(", version = ").append(version)
          .append(")")
          .toString()
}