package com.saintdan.framework.po

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.data.annotation.CreatedDate
import org.springframework.http.HttpMethod
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
          (Parameter(name = "sequence_name", value = "logs_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "logSequenceGenerator")
    @Column(updatable = false)
    val id: Long,

    @Column(nullable = false, length = 50)
    val ip: String,

    @Column(nullable = false)
    val usr: String,

    val clientId: String? = "",
    val path: String? = "",
    val method: HttpMethod = HttpMethod.GET,

    @CreatedDate
    @Column(nullable = false)
    val createdAt: Long = System.currentTimeMillis()
)