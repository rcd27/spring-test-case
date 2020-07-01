package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

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
}