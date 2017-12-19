package com.saintdan.framework.po

import javax.persistence.Entity
import javax.persistence.Table

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "tests")
data class Test(
    val id: Long = 0,
    val name: String = ""
)