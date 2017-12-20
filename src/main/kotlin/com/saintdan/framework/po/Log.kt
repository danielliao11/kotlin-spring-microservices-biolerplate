package com.saintdan.framework.po

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.data.annotation.CreatedDate
import org.springframework.http.HttpMethod
import java.io.Serializable
import javax.persistence.*

/**
 * Log, record users' behavior.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "logs")
data class Log(
    @GenericGenerator(name = "logSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          Parameter(name = "sequence_name", value = "logs_seq"),
          Parameter(name = "initial_value", value = "1"),
          Parameter(name = "increment_size", value = "1")])
    @Id
    @GeneratedValue(generator = "logSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(nullable = false, length = 50)
    val ip: String,

    @Column(nullable = false)
    val usr: String,

    @Column(updatable = false)
    val clientId: String = "",

    @Column(updatable = false)
    val path: String = "",

    @Column(updatable = false, length = 10)
    val method: String = HttpMethod.GET.name,

    @CreatedDate
    @Column(nullable = false)
    val createdAt: Long = System.currentTimeMillis()
) : Serializable {
  companion object {
    private const val serialVersionUID = -3885319431007004288L
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val log = other as Log?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, log!!.id)
        .append(createdAt, log.createdAt)
        .append(ip, log.ip)
        .append(usr, log.usr)
        .append(clientId, log.clientId)
        .append(path, log.path)
        .append(method, log.method)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(ip)
        .append(usr)
        .append(clientId)
        .append(path)
        .append(method)
        .append(createdAt)
        .toHashCode()
  }
}