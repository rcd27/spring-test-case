package com.github.rcd27.api.idgeneration.domain

import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IdGenerationService(private val repository: IdGenerationRepository) {
    fun getUniqueId(): Mono<String> =
            repository.generateUniqueId()
}