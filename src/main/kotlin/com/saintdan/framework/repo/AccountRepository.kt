package com.saintdan.framework.repo

import com.saintdan.framework.po.Account
import java.util.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
interface AccountRepository : CustomRepository<Account, Long> {
  fun findByAccount(account: String): Optional<Account>
}