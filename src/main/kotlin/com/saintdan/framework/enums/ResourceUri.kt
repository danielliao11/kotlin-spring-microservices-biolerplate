package com.saintdan.framework.enums

import com.saintdan.framework.constant.ResourcePath
import com.saintdan.framework.param.ClientParam
import com.saintdan.framework.param.ResourceParam
import com.saintdan.framework.param.RoleParam
import com.saintdan.framework.param.UserParam
import java.util.*

/**
 * Resource uri enums.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
enum class ResourceUri constructor(private val uri: String, private val clazz: Class<*>) {

  LOGIN(ResourcePath.API + ResourcePath.OPEN + ResourcePath.LOGIN, ResourceParam::class.java),
  CLIENT(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.CLIENTS, ClientParam::class.java),
  USER(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.USERS, UserParam::class.java),
  ROLE(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.ROLES, RoleParam::class.java),
  RESOURCE(ResourcePath.API + ResourcePath.MANAGEMENT + ResourcePath.RESOURCES, ResourceParam::class.java),
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