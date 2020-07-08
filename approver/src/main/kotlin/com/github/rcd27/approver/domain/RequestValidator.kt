package com.github.rcd27.approver.domain

import com.github.rcd27.approver.dto.ApprovalRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
object RequestValidator {

    fun validate(input: ApprovalRequest): Mono<ValidationResult> {

        // TODO: implement
        return Mono.just(Valid)
    }
}

sealed class ValidationResult
object Valid : ValidationResult()
object Invalid : ValidationResult()