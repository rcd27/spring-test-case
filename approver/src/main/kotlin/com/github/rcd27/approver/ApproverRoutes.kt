package com.github.rcd27.approver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType

@Configuration
open class ApproverRoutes(private val approverHandler: ApproverHandler) {

  @Bean
  open fun router() = org.springframework.web.reactive.function.server.router {
    (accept(MediaType.APPLICATION_JSON) and "/api/v1/").nest {
      POST("/approve", approverHandler::approve)
    }
  }
}