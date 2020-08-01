package com.github.rcd27.idgenerator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
open class IdGeneratorRoutes(
    private val idGeneratorHandler: IdGeneratorHandler
) {

  @Bean
  open fun router() = router {
    (accept(MediaType.APPLICATION_JSON) and "/api/v1/").nest {
      "/id".nest {
        GET("/generate", idGeneratorHandler::generateUniqueId)
      }
    }
  }
}