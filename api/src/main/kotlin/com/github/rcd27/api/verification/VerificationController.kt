package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class VerificationController(
    private val idGenerationUseCase: IdGenerationUseCase,
    private val verificationUseCase: VerificationUseCase,
    private val verificationStatusUseCase: VerificationStatusUseCase
) {

  @PostMapping("/verify")
  fun verify(@RequestBody input: Mono<VerificationRequest>): Mono<String> =
      idGenerationUseCase.getUniqueId()
          .flatMap {
            uniqueId -> verificationUseCase.verify(uniqueId, input) }
          .map { it.toString() }

  @GetMapping("/status/{id}")
  fun checkVerificationStatus(@PathVariable id: String): Mono<String> =
      verificationStatusUseCase.checkVerificationStatus(id)
}