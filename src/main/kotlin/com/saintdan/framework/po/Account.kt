package com.saintdan.framework.po

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.*

/**
 * Account of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "accounts")
data class Account(
    @GenericGenerator(name = "accountSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          (Parameter(name = "sequence_name", value = "accounts_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "accountSequenceGenerator")
    @Column(updatable = false)
    val id: Long,

    val account: String,

    @Column(updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    val createdBy: Long = 0,

    @Column(nullable = false)
    val lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    val lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    val version: Int = 0
)