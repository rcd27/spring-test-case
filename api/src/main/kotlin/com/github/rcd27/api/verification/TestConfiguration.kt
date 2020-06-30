package com.github.rcd27.api.verification

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
open class TestConfiguration {

    @Bean
    open fun testRouterFunction(): RouterFunction<ServerResponse> {
        return route()
            .GET("/test") {
                ServerResponse.ok().build()
            }
            .build()
    }
}