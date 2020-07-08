package com.github.rcd27.approver.domain

import com.github.rcd27.approver.dto.ApprovalRequest
import com.github.rcd27.approver.dto.ApprovalResult
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@EnableRabbit
class ApproverService(private val requestValidator: RequestValidator) {

    fun sendForApproval(input: ApprovalRequest): Mono<ApprovalResult> {
        return requestValidator.validate(input)
            .map { validationResult: ValidationResult ->
                when (validationResult) {
                    is Valid -> {
                        // TODO: rabbit send for mailer
                        ApprovalResult(
                            input,
                            "Approved"
                        )
                    }

                    is Invalid ->
                        ApprovalResult(
                            input,
                            "Denied"
                        )
                }
            }
    }
}
