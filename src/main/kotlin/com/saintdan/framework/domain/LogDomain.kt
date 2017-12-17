package com.saintdan.framework.domain

import com.saintdan.framework.po.Log
import com.saintdan.framework.repo.LogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
class LogDomain @Autowired constructor(private val logRepository: LogRepository) {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Async
  @Transactional
  fun create(log: Log) {
    logRepository.save(log)
  }

  /**
   * Show logs.
   *
   * @return [Page]
   * @throws CommonsException [com.saintdan.framework.enums.ErrorType.SYS0121] No group
   * exists.
   */
  @Throws(Exception::class)
  fun page(specification: Specification<Log>, pageable: Pageable): Page<*> {
    val logs = logRepository.findAll(specification, pageable)
    return if (!logs.hasContent()) {
      PageImpl(ArrayList<Any>(), pageable, logs.totalElements)
    } else logs
  }
}