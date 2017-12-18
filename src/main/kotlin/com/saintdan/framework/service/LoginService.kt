package com.saintdan.framework.service

import com.saintdan.framework.param.LoginParam
import javax.servlet.http.HttpServletRequest

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 18/12/2017
 * @since JDK1.8
 */
interface LoginService {

  fun login(param: LoginParam, request: HttpServletRequest)

  fun refresh(param: LoginParam, request: HttpServletRequest)
}