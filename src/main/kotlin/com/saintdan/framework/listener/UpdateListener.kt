package com.saintdan.framework.listener

import com.saintdan.framework.constant.CommonsConstant
import com.saintdan.framework.po.User
import com.saintdan.framework.tool.SpringSecurityUtils
import org.apache.commons.beanutils.BeanUtilsBean2
import java.lang.reflect.InvocationTargetException
import javax.persistence.PreUpdate

/**
 * Persistent created time.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
class UpdateListener {

  @PreUpdate
  fun onCreate(`object`: Any) {
    val beanUtilsBean = BeanUtilsBean2.getInstance()
    val user = SpringSecurityUtils.getCurrentUser<User>() !!
    try {
      beanUtilsBean.setProperty(`object`, CommonsConstant.LAST_MODIFIED_AT, System.currentTimeMillis())
      beanUtilsBean.setProperty(`object`, CommonsConstant.LAST_MODIFIED_BY, user.id)
    } catch (ignore: IllegalAccessException) {
    } catch (ignore: InvocationTargetException) {
    } catch (ignore: NoSuchMethodException) {
    }

  }
}