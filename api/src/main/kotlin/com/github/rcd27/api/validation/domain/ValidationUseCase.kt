package com.github.rcd27.api.validation.domain

import com.github.rcd27.api.entities.dto.VerificationRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.validation.Validator
import reactor.core.publisher.Mono

interface ValidationUseCase {
  // FIXME: (VerificationRequest) -> Validated<VerificationRequest>
  fun validateVerificationRequest(input: VerificationRequest): Mono<Unit>
}

@Service
class ValidationService(
    @Qualifier("provideVerificationRequestValidator") private val verificationRequestValidator: Validator
) : ValidationUseCase {

  override fun validateVerificationRequest(input: VerificationRequest): Mono<Unit> {
    TODO("implement")
  }
}