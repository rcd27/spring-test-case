package com.github.rcd27.api.approval.data

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class ApprovalRepository(private val webClient: WebClient) {

    fun sendForApproval(approvalRequest: ApprovalRequest): Mono<ApprovalResult> =
        webClient.post()
            .uri("http://approver:8082/api/v1/approve")
            .body(Mono.just(approvalRequest), ApprovalRequest::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApprovalResult::class.java)

}