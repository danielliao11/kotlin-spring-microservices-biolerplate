package com.saintdan.framework.service.impl

import com.saintdan.framework.param.LoginParam
import com.saintdan.framework.service.LoginService
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
@Service
class LoginServiceImpl : LoginService {
  override fun login(param: LoginParam, request: HttpServletRequest) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun refresh(param: LoginParam, request: HttpServletRequest) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}