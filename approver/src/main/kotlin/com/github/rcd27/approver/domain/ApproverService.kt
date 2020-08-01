package com.github.rcd27.approver.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.rcd27.approver.dto.ApprovalRequest
import com.github.rcd27.approver.dto.ApprovalResponse
import com.github.rcd27.approver.dto.ApprovalStatus
import com.github.rcd27.approver.dto.MailerRequest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
class ApproverService(
    private val requestValidator: RequestValidator,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

  fun sendForApproval(input: ApprovalRequest): Mono<ApprovalResponse> {
    /** For simplicity - validation is mocked and every request is approved */
    return requestValidator.validate(input)
        .map { validationResult: ValidationResult ->
          when (validationResult) {
            is Valid -> {

              val json: String = jacksonObjectMapper().writeValueAsString(
                  MailerRequest(input.verificationId, "Approved")
              )
              kafkaTemplate.send("mailerTopic", json)

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
