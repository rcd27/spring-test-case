package com.github.rcd27.approver

import com.github.rcd27.approver.domain.ApproverService
import com.github.rcd27.approver.dto.ApprovalResult
import com.github.rcd27.approver.dto.ApprovalRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ApproverHandler(private val approverService: ApproverService) {

    fun approve(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ApprovalRequest::class.java)
            .flatMap { approverService.sendForApproval(it) }
            .flatMap { approvalResult: ApprovalResult ->
                ServerResponse.ok().body(Mono.just(approvalResult), ApprovalResult::class.java)
            }
}
