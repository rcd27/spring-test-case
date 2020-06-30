package com.github.rcd27.api.idgeneration.domain

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface IdGenerationUseCase {

  fun getUniqueId(input: VerificationRequest): Mono<String>

  @Service
  class IdGenerationService(private val repository: IdGenerationRepository) : IdGenerationUseCase {

    // TODO: подумать над f(n) -> y
    override fun getUniqueId(input: VerificationRequest): Mono<String> =
      repository.generateUniqueId()
  }
}