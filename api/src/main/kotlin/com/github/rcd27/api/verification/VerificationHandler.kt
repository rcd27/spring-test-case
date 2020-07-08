package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationService
import com.github.rcd27.api.verification.domain.VerificationService
import com.github.rcd27.api.verification.domain.VerificationStatusService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class VerificationHandler(
        private val idGenerationService: IdGenerationService,
        private val verificationService: VerificationService,
        private val verificationStatusService: VerificationStatusService
) {

    fun verify(request: ServerRequest): Mono<ServerResponse> =
            request.bodyToMono(VerificationRequest::class.java).flatMap { input ->
                idGenerationService.getUniqueId()
                        .flatMap { uniqueId -> verificationService.verify(uniqueId, input) }
                        .map { it.toString() }
                        .flatMap { ServerResponse.ok().bodyValue(it) }
            }

    @Suppress("ReactiveStreamsUnusedPublisher")
    fun getStatus(request: ServerRequest): Mono<ServerResponse> {
        return try {
            val id = request.pathVariable("id")
            verificationStatusService
                    .checkVerificationStatus(id)
                    .flatMap {
                        ServerResponse.ok().bodyValue(it)
                    }
                    .onErrorResume {
                        when (it) {
                            is IllegalArgumentException -> ServerResponse.badRequest().bodyValue(it.message
                                    ?: "")
                            else -> ServerResponse.badRequest().build()
                        }
                    }
        } catch (e: IllegalArgumentException) {
            // TODO: describe what went wrong, so user can handle
            ServerResponse.badRequest().build()
        }
    }
}
