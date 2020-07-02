package com.github.rcd27.api

import com.github.rcd27.api.verification.VerificationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
open class ApiRoutes(private val verificationHandler: VerificationHandler) {

    @Bean
    open fun apiRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/api/v1").nest {
            "/verification".nest {
                POST("/verify", verificationHandler::verify)
                GET("/status/{id}", verificationHandler::getStatus)
            }
        }
    }
}