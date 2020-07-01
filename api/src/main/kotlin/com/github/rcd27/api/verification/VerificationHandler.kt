package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class VerificationHandler(
    private val idGenerationUseCase: IdGenerationUseCase,
    private val verificationUseCase: VerificationUseCase,
    private val verificationStatusUseCase: VerificationStatusUseCase
) {

  fun verify(request: ServerRequest): Mono<ServerResponse> =
      request.bodyToMono(VerificationRequest::class.java).flatMap { input ->
        idGenerationUseCase.getUniqueId()
            .flatMap { uniqueId -> verificationUseCase.verify(uniqueId, input) }
            .map { it.toString() }
            .flatMap { ServerResponse.ok().bodyValue(it) }
      }

  fun getStatus(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().build()

}