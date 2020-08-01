@file:Suppress("UNUSED_PARAMETER")

package com.github.rcd27.idgenerator

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
object IdGeneratorHandler {

  fun generateUniqueId(request: ServerRequest): Mono<ServerResponse> =
      ServerResponse.ok().bodyValue(UUID.randomUUID().toString())

}