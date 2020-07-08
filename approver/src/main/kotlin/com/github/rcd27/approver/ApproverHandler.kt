package com.github.rcd27.approver

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ApproverHandler {

  fun approve(request: ServerRequest): Mono<ServerResponse> {
    TODO("")
  }
}
