package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.validation.domain.ValidationUseCase
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/")
class VerificationController(
    private val verificationUseCase: VerificationUseCase,
    private val verificationStatusUseCase: VerificationStatusUseCase,
    private val idGenerationUseCase: IdGenerationUseCase,
    private val validationUseCase: ValidationUseCase
) {

    @PostMapping("verify")
    fun verify(@RequestBody input: VerificationRequest): Mono<String> =
        validationUseCase.validateVerificationRequest(input)
            .flatMap { idGenerationUseCase.getUniqueId(input) }
            .flatMap { uniqueId -> verificationUseCase.verify(uniqueId, input) }
            .map { it.toString() }

}