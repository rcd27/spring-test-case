package com.github.rcd27.approver

import com.github.rcd27.approver.domain.ApproverService
import com.github.rcd27.approver.dto.Approval
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
            .flatMap { approval: Approval ->
                ServerResponse.ok().body(Mono.just(approval), Approval::class.java)
            }
}
