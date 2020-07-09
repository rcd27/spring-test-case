package com.github.rcd27.approver.domain

import com.github.rcd27.approver.dto.ApprovalRequest
import com.github.rcd27.approver.dto.ApprovalResponse
import com.github.rcd27.approver.dto.ApprovalStatus
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@EnableRabbit
class ApproverService(private val requestValidator: RequestValidator) {

    fun sendForApproval(input: ApprovalRequest): Mono<ApprovalResponse> {
        /** For simplicity - validation is mocked and every request is approved */
        return requestValidator.validate(input)
            .map { validationResult: ValidationResult ->
                when (validationResult) {
                    is Valid -> {
                        // TODO: rabbit send for mailer
                        ApprovalResponse(
                            input,
                            ApprovalStatus.Approved
                        )
                    }

                    is Invalid ->
                        ApprovalResponse(
                            input,
                            ApprovalStatus.Denied
                        )
                }
            }
    }
}
