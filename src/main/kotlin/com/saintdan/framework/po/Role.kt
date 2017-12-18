package com.saintdan.framework.po

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saintdan.framework.listener.PersistentListener
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.*

/**
 * Authorized roles, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "roles")
@NamedEntityGraph(name = "Role.resources", attributeNodes = [NamedAttributeNode("resources")])
@EntityListeners(PersistentListener::class)
data class Role(

    @GenericGenerator(name = "roleSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          (Parameter(name = "sequence_name", value = "roles_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "roleSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(unique = true, nullable = false, length = 20)
    val name: String = "",

    @Column(length = 500)
    val description: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val createdBy: Long = 0,

    @Column(nullable = false)
    @JsonIgnore
    val lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    @JsonIgnore
    val lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    @JsonIgnore
    val version: Int = 0,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = [CascadeType.REFRESH])
    @JsonIgnore
    val users: Set<User>? = emptySet(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REFRESH])
    @JoinTable(
        name = "roles_has_resources",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "resource_id")])
    @JsonIgnore
    val resources: Set<Resource>? = emptySet()
) {
  @PreRemove
  private fun removeRolesFromUsers() {
    users!!.forEach { user -> user.roles.minus(this) }
  }
}