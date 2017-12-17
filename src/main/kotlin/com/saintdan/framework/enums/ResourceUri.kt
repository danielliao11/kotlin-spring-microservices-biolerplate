package com.saintdan.framework.enums

import java.util.*

/**
 * Resource uri enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
enum class ResourceUri constructor(private val uri: String, private val clazz: Class<*>) {

  UNKNOWN("unknown", Nothing::class.java);

  val isUnknown: Boolean
    get() = ResourceUri.UNKNOWN.uri == this.uri

  fun uri(): String {
    return uri
  }

  fun clazz(): Class<*> {
    return clazz
  }

  fun matches(uri: String): Boolean {
    return this == resolve(uri)
  }

  companion object {

    private val mappings = HashMap<String, ResourceUri>(2)

    init {
      for (resourceUri in values()) {
        mappings.put(resourceUri.uri, resourceUri)
      }
    }

    fun resolve(uri: String): ResourceUri {
      val matchUri = mappings.keys.stream()
          .filter({ uri.contains(it) })
          .findFirst()
          .orElse(null)
      return if (matchUri != null) mappings.getValue(matchUri) else UNKNOWN
    }
  }

}