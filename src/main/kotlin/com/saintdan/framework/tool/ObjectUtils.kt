package com.saintdan.framework.tool

import org.apache.commons.lang3.ArrayUtils
import java.math.BigDecimal

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 1/2/2018
 * @since JDK1.8
 */

object ObjectUtils {

  fun parse(param: String?, value: String?): String? = if (isEmpty(param)) value else param

  fun parse(param: Array<*>?, value: Array<*>?): Array<*>?  = if (isEmpty(param)) value else param

  fun parse(param: Set<*>?, value: Set<*>?): Set<*>? = if (isEmpty(param)) value else param

  fun parse(param: BigDecimal?, value: BigDecimal?): BigDecimal? = if (isEmpty(param)) value else param

  fun parse(param: Int?, value: Int?): Int? = if (isEmpty(param)) value else param

  fun parse(param: Long?, value: Long?): Long? = if (isEmpty(param)) value else param

  fun parse(param: Double?, value: Double?): Double? = if (isEmpty(param)) value else param

  fun parse(param: Float?, value: Float?): Float? = if (isEmpty(param)) value else param

  fun parse(param: Short?, value: Short?): Short? = if (isEmpty(param)) value else param

  fun parse(param: Byte?, value: Byte?): Byte? = if (isEmpty(param)) value else param

  fun parse(param: Boolean?, value: Boolean?): Boolean? = if (isEmpty(param)) value else param

  fun isEmpty(v: String?): Boolean = v.isNullOrBlank()

  fun isEmpty(v: Array<*>?): Boolean = ArrayUtils.isEmpty(v)

  fun isEmpty(v: Set<*>?): Boolean = v == null || v.isEmpty()

  fun isEmpty(v: BigDecimal?): Boolean = v == null

  fun isEmpty(v: Int?): Boolean = v == null

  fun isEmpty(v: Long?): Boolean = v == null

  fun isEmpty(v: Double?): Boolean = v == null

  fun isEmpty(v: Float?): Boolean = v == null

  fun isEmpty(v: Short?): Boolean = v == null

  fun isEmpty(v: Byte?): Boolean = v == null

  fun isEmpty(v: Boolean?): Boolean = v == null

}
