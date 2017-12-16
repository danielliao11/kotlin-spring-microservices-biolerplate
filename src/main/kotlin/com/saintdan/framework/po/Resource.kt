package com.saintdan.framework.po

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

/**
 * Authorized resources, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "resources")
@NamedEntityGraph(name = "Resource.roles", attributeNodes = [NamedAttributeNode("roles")])
data class Resource(

    @GenericGenerator(name = "resourceSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          (Parameter(name = "sequence_name", value = "resources_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "resourceSequenceGenerator")
    @Column(updatable = false)
    private val id: Long,

    @Column(unique = true, nullable = false, length = 20)
    private val name: String,

    @Column(length = 500)
    private val description: String,

    @Column(nullable = false, updatable = false)
    private val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    private val createdBy: Long = 0,

    @Column(nullable = false)
    private val lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    private val lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    private val version: Int = 0,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = [(CascadeType.REFRESH)])
    private val roles: Set<Role> = emptySet()
) : GrantedAuthority {
  override fun getAuthority(): String {
    return name
  }
}