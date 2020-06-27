package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.entities.persist.VerificationProcess
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface VerificationStatusUseCase {

  fun checkVerificationStatus(): Mono<VerificationProcess>

  @Service
  class VerificationStatusService : VerificationStatusUseCase {

    override fun checkVerificationStatus(): Mono<VerificationProcess> {
      TODO("implement")
    }
  }
}