package com.saintdan.framework.domain

import com.saintdan.framework.po.ErrorLog
import com.saintdan.framework.repo.ErrorLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
class ErrorLogDomain @Autowired constructor(private val errorLogRepository: ErrorLogRepository) {

  @Async
  @Transactional
  fun create(errorLog: ErrorLog) {
    errorLogRepository.save(errorLog)
  }

  /**
   * Show logs.
   *
   * @return [Page]
   * exists.
   */
  fun page(specification: Specification<ErrorLog>, pageable: Pageable): Page<*> {
    val logs = errorLogRepository.findAll(specification, pageable)
    return if (!logs.hasContent()) {
      PageImpl(ArrayList<Any>(), pageable, logs.totalElements)
    } else logs
  }
}