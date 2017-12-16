package com.saintdan.framework.listener

import com.saintdan.framework.constant.CommonsConstant
import org.apache.commons.beanutils.BeanUtilsBean2
import java.lang.reflect.InvocationTargetException
import javax.persistence.PrePersist

/**
 * Persistent created time.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
class PersistentListener {

  @PrePersist
  fun onCreate(`object`: Any) {
    val beanUtilsBean = BeanUtilsBean2.getInstance()
    try {
      if (beanUtilsBean.getProperty(`object`, CommonsConstant.ID) == CommonsConstant.ZERO) {
        beanUtilsBean.setProperty(`object`, CommonsConstant.CREATED_AT, System.currentTimeMillis())
        beanUtilsBean.setProperty(`object`, CommonsConstant.LAST_MODIFIED_AT, System.currentTimeMillis())
      }
    } catch (ignore: IllegalAccessException) {
    } catch (ignore: InvocationTargetException) {
    } catch (ignore: NoSuchMethodException) {
    }

  }
}