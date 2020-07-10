package com.github.rcd27.approver.domain

import com.github.rcd27.approver.dto.ApprovalRequest
import com.github.rcd27.approver.dto.ApprovalResponse
import com.github.rcd27.approver.dto.ApprovalStatus
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@EnableRabbit
class ApproverService(
    private val requestValidator: RequestValidator,
    @Autowired private val rabbitTemplate: RabbitTemplate
) {

    fun sendForApproval(input: ApprovalRequest): Mono<ApprovalResponse> {
        /** For simplicity - validation is mocked and every request is approved */
        return requestValidator.validate(input)
            .map { validationResult: ValidationResult ->
                when (validationResult) {
                    is Valid -> {

                        // FIXME: выглядит уёбищно, подумать как лучше
                        val json = "{\"id\" : \"${input.verificationId}\",\n\"status\" : \"Approved\" }"
                        val jsonMessage = MessageBuilder.withBody(json.toByteArray())
                            .andProperties(
                                MessagePropertiesBuilder.newInstance().setContentType("application/json")
                                    .build()
                            ).build()
                        rabbitTemplate.send("mailerQueue", jsonMessage)

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
