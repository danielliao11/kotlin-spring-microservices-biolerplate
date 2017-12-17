package com.saintdan.framework.listener

import com.saintdan.framework.constant.CommonsConstant
import com.saintdan.framework.repo.UserRepository
import com.saintdan.framework.tool.SpringSecurityUtils
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
class PersistentListener(private val userRepository: UserRepository) {

  @PrePersist
  fun onCreate(`object`: Any) {
    val beanUtilsBean = BeanUtilsBean2.getInstance()
    try {
        userRepository.findByUsr(SpringSecurityUtils.getCurrentUsername())
            .map {
              if (beanUtilsBean.getProperty(`object`, CommonsConstant.ID) == CommonsConstant.ZERO) {
                beanUtilsBean.setProperty(`object`, CommonsConstant.CREATED_BY, it.id)
              }
              beanUtilsBean.setProperty(`object`, CommonsConstant.LAST_MODIFIED_AT, System.currentTimeMillis())
              beanUtilsBean.setProperty(`object`, CommonsConstant.LAST_MODIFIED_BY, it.id)
            }
    } catch (ignore: IllegalAccessException) {
    } catch (ignore: InvocationTargetException) {
    } catch (ignore: NoSuchMethodException) {
    }

  }
}