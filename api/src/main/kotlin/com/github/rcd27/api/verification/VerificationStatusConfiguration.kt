package com.github.rcd27.api.verification

import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body

@Configuration
open class VerificationStatusConfiguration {

  @Bean
  open fun verificationStatusRoute(verificationStatusUseCase: VerificationStatusUseCase): RouterFunction<ServerResponse> {
    return route()
      // FIXME: replace with POST logic
      .GET("/status") {
        ServerResponse.ok().body(
          verificationStatusUseCase.checkVerificationStatus()
        )
      }
      .build()
  }
}