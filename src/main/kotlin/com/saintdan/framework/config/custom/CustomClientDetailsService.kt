package com.saintdan.framework.config.custom

import com.saintdan.framework.repo.ClientRepository
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.stereotype.Service

/**
 *
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 19/12/2017
 * @since JDK1.8
 */
@Service
class CustomClientDetailsService (private val clientRepository: ClientRepository) : ClientDetailsService {

  @Throws(ClientRegistrationException::class)
  override fun loadClientByClientId(clientId: String): ClientDetails {
    return clientRepository.findByClientIdAlias(clientId)
        .orElseThrow { throw ClientRegistrationException(String.format("Client %s does not exist!", clientId)) }
  }
}