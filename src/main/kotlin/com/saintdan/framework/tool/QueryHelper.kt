package com.saintdan.framework.tool

import com.saintdan.framework.constant.CommonsConstant
import com.saintdan.framework.param.BaseParam
import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.*

/**
 * Query helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/12/2017
 * @since JDK1.8
 */
object QueryHelper {

  fun getPageRequest(param: BaseParam): PageRequest {
    return PageRequest.of(param.pageNo ?: 1 - 1,
        param.pageSize ?: 20, QueryHelper.getSort(param.sortBy ?: "id:desc"))
  }

  /**
   * Get default [Sort].
   *
   * @return [Sort]
   */
  fun getDefaultSort(): Sort {
    return Sort.by(Sort.Direction.ASC, "id")
  }

  /**
   * Get [Sort]
   *
   * @param param     sort param
   * @param direction [Sort.Direction]
   * @return [Sort]
   */
  fun getSort(param: String, direction: Sort.Direction): Sort {
    return Sort.by(direction, param)
  }

  /**
   * Get [Sort]
   *
   * @param map sort map
   * @return [Sort]
   */
  fun getSort(map: TreeMap<String, Sort.Direction>): Sort {
    val orderList = ArrayList<Sort.Order>()
    for ((key, value) in map) {
      val order = Sort.Order(value, key)
      orderList.add(order)
    }
    return Sort.by(orderList)
  }

  /**
   * Get [Sort]
   *
   * @param sortBy sortedBy
   * @return [Sort]
   */
  fun getSort(sortBy: String?): Sort {
    return if (StringUtils.isBlank(sortBy))
      getDefaultSort()
    else {
      Sort.by(sortBy!!.split(CommonsConstant.COMMA)
          .dropLastWhile { it.isBlank() }
          .map { orders -> getOrder(orders.split(CommonsConstant.COLON)
              .dropLastWhile { it.isBlank() }.toTypedArray()) })

    }
  }

  /**
   * Get [Sort.Order]
   *
   * @param orders orders
   * @return [Sort.Order]
   */
  private fun getOrder(orders: Array<String>): Sort.Order {
    return Sort.Order(Sort.Direction.fromString(orders[1]), orders[0])
  }
}