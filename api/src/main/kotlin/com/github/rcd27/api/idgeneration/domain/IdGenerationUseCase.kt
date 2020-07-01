package com.github.rcd27.api.idgeneration.domain

import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface IdGenerationUseCase {
  fun getUniqueId(): Mono<String>
}

@Service
class IdGenerationService(private val repository: IdGenerationRepository) : IdGenerationUseCase {
  override fun getUniqueId(): Mono<String> =
      repository.generateUniqueId()
}