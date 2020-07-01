package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class VerificationController(
    private val verificationUseCase: VerificationUseCase,
    private val idGenerationUseCase: IdGenerationUseCase
) {

  @PostMapping("verify")
  fun verify(@RequestBody input: VerificationRequest): Mono<String> =
      idGenerationUseCase.getUniqueId(input)
          .flatMap { uniqueId -> verificationUseCase.verify(uniqueId, input) }
          .map { it.toString() }

}