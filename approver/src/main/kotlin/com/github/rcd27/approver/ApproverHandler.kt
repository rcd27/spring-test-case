package com.github.rcd27.approver

import com.github.rcd27.approver.domain.ApproverService
import com.github.rcd27.approver.dto.ApprovalRequest
import com.github.rcd27.approver.dto.ApprovalResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ApproverHandler(private val approverService: ApproverService) {

  fun approve(request: ServerRequest): Mono<ServerResponse> =
      request.bodyToMono(ApprovalRequest::class.java)
          .flatMap { approverService.sendForApproval(it) }
          .flatMap { approvalResponse: ApprovalResponse ->
            ServerResponse.ok().body(Mono.just(approvalResponse), ApprovalResponse::class.java)
          }
}
