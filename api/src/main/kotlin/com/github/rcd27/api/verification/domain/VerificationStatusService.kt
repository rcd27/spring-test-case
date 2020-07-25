package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class VerificationStatusService(private val verificationProcessRepository: VerificationProcessRepository) {

    fun checkVerificationStatus(id: String): Mono<String> =
            verificationProcessRepository.findById(id)
                    .map { it.status.toString() }
                    .switchIfEmpty {
                        Mono.error(NoProcessForIdException(id))
                    }
}

class NoProcessForIdException(val id: String) : Throwable("No process for such id: $id")