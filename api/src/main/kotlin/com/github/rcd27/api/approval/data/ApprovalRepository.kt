package com.github.rcd27.api.approval.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class ApprovalRepository(
    private val webClient: WebClient,
    @Value("\${api.approverUrl:http://approver:8082}") private val approverUrl: String
) {

    fun sendForApproval(approvalRequest: ApprovalRequest): Mono<ApprovalResponse> =
        webClient.post()
            .uri("$approverUrl/api/v1/approve")
            .body(Mono.just(approvalRequest), ApprovalRequest::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApprovalResponse::class.java)

}