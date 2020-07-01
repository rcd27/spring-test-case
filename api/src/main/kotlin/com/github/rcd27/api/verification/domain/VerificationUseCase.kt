package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface VerificationUseCase {
  /**
   * returns an unique id of particular [VerificationProcess]
   */
  fun verify(uniqueId: String, input: VerificationRequest): Mono<String>
}

// TODO: send a verification message via RabbitMQ, then save to db and return the ID
@Service
class VerificationService(private val processRepository: VerificationProcessRepository) : VerificationUseCase {

  override fun verify(uniqueId: String, input: VerificationRequest): Mono<String> =
      processRepository.save(
          VerificationProcess(
              uniqueId,
              VerificationProcess.VerificationStatus.IN_PROGRESS
          )
      )
          .map { uniqueId }
}