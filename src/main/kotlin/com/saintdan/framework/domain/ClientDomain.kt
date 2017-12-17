package com.saintdan.framework.domain

import com.saintdan.framework.constant.AuthorityConstant
import com.saintdan.framework.param.ClientParam
import com.saintdan.framework.po.Client
import com.saintdan.framework.repo.ClientRepository
import com.saintdan.framework.tool.Generator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
class ClientDomain(private val clientRepository: ClientRepository) {
  @Transactional fun create(param: ClientParam): Optional<Client> {
    return param2Po(param)
        .map { clientRepository.save(it) }
  }

  fun all(): MutableList<Client> {
    return clientRepository.findAll()
  }

  @Throws(Exception::class)
  @Transactional fun update(param: ClientParam): Optional<Client> {
    return clientRepository.findById(param.id)
        .flatMap { param2Po(param, it) }
        .map { clientRepository.save(it) }
  }

  private fun param2Po(param: ClientParam): Optional<Client> {
    return Optional.of(Client(
        name = param.name ?: "",
        clientIdAlias = Generator.generatorOfLetterAndDigit().generate(16),
        clientSecretAlias = Generator.generatorOfLetterAndDigit().generate(32),
        resourceIdStr = AuthorityConstant.RESOURCE_ID,
        scopeStr = param.scope ?: AuthorityConstant.SCOPE,
        authorizedGrantTypeStr = param.grantType ?: AuthorityConstant.GRANT_TYPE,
        authoritiesStr = AuthorityConstant.AUTHORITY,
        accessTokenValiditySecondsAlias = param.accessTokenValiditySeconds ?: 0,
        refreshTokenValiditySecondsAlias = param.refreshTokenValiditySeconds ?: 0
    ))
  }

  private fun param2Po(param: ClientParam, client: Client): Optional<Client> {
    return Optional.of(client.copy(
        name = param.name ?: client.name,
        accessTokenValiditySecondsAlias = param.accessTokenValiditySeconds ?: client.accessTokenValiditySecondsAlias,
        refreshTokenValiditySecondsAlias = param.refreshTokenValiditySeconds ?: client.refreshTokenValiditySecondsAlias
    ))
  }
}