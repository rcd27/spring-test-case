package com.github.rcd27.approver.domain

import com.github.rcd27.approver.dto.Approval
import com.github.rcd27.approver.dto.Approved
import com.github.rcd27.approver.dto.Denied
import com.github.rcd27.approver.dto.ApprovalRequest
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@EnableRabbit
class ApproverService(private val requestValidator: RequestValidator) {

    fun sendForApproval(input: ApprovalRequest): Mono<Approval> {
        return requestValidator.validate(input)
            .map { validationResult: ValidationResult ->
                when (validationResult) {
                    is Valid -> {
                        // TODO: rabbit send for mailer
                        Approval(
                            "",
                            input,
                            "Approved"
                        )
                    }

                    is Invalid ->
                        Approval(
                            "",
                            input,
                            "Denied"
                        )
                }
            }
    }
}
