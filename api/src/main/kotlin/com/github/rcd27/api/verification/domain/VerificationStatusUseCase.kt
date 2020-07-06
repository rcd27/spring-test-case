package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

interface VerificationStatusUseCase {
  fun checkVerificationStatus(id: String): Mono<String>
}

@Service
class VerificationStatusService(
    private val verificationProcessRepository: VerificationProcessRepository
) : VerificationStatusUseCase {

  override fun checkVerificationStatus(id: String): Mono<String> =
      verificationProcessRepository.findById(id)
          .map { it.status.toString() }
          .switchIfEmpty {
            // TODO: describe domain-specific errors with sealed classes
            Mono.error(IllegalArgumentException("No process for such id: $id"))
          }

}