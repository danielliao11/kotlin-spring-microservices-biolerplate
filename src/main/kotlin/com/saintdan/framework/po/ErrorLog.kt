package com.saintdan.framework.po

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.data.annotation.CreatedDate
import org.springframework.http.HttpMethod
import javax.persistence.*

/**
 * Log for error.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "error_logs")
data class ErrorLog(
    @GenericGenerator(name = "errorLogSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          (Parameter(name = "sequence_name", value = "error_logs_seq")),
          (Parameter(name = "initial_value", value = "1")),
          (Parameter(name = "increment_size", value = "1"))])
    @Id
    @GeneratedValue(generator = "errorLogSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(updatable = false)
    val name: String = "",

    @Column(nullable = false, length = 50)
    val ip: String,

    @Column(nullable = false)
    val usr: String,

    @Column(updatable = false)
    val clientId: String = "",

    @Column(updatable = false)
    val path: String = "",

    @Column(updatable = false)
    val method: String = HttpMethod.GET.name,

    @CreatedDate
    @Column(nullable = false)
    val createdAt: Long = System.currentTimeMillis()
)