package com.saintdan.framework.repo

import com.saintdan.framework.po.Resource
import java.util.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
interface ResourceRepository : CustomRepository<Resource, Long> {
  fun findByName(name: String): Optional<Resource>
}