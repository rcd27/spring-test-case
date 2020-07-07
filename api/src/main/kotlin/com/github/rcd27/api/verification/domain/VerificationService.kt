package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class VerificationService(private val processRepository: VerificationProcessRepository) {

    fun verify(uniqueId: String, input: VerificationRequest): Mono<String> =
        processRepository.save(
            VerificationProcess(
                uniqueId,
                VerificationProcess.VerificationStatus.IN_PROGRESS
            )
        )
            .map { uniqueId }
}