package com.saintdan.framework.po

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saintdan.framework.listener.UpdateListener
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.io.Serializable
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
@EntityListeners(UpdateListener::class)
data class Account(
    @GenericGenerator(name = "accountSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          Parameter(name = "sequence_name", value = "accounts_seq"),
          Parameter(name = "initial_value", value = "1"),
          Parameter(name = "increment_size", value = "1")])
    @Id
    @GeneratedValue(generator = "accountSequenceGenerator")
    @Column(updatable = false)
    val id: Long,

    val account: String,

    @Column(updatable = false)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    val user: User? = null
) : Serializable {
  companion object {
    private const val serialVersionUID = -6004454109313475045L
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val accountObj = other as Account?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, accountObj!!.id)
        .append(createdAt, accountObj.createdAt)
        .append(createdBy, accountObj.createdBy)
        .append(lastModifiedAt, accountObj.lastModifiedAt)
        .append(lastModifiedBy, accountObj.lastModifiedBy)
        .append(version, accountObj.version)
        .append(account, accountObj.account)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(account)
        .append(createdAt)
        .append(createdBy)
        .append(lastModifiedAt)
        .append(lastModifiedBy)
        .append(version)
        .toHashCode()
  }

  override fun toString(): String =
    StringBuilder("Account(")
        .append("id = ").append(id)
        .append(", account = ").append(account)
        .append(", createdAt = ").append(createdAt)
        .append(", createdBy = ").append(createdBy)
        .append(", lastModifiedAt = ").append(lastModifiedAt)
        .append(", lastModifiedBy = ").append(lastModifiedBy)
        .append(", version = ").append(version)
        .append(")")
        .toString()
}