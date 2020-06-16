package com.github.rcd27.springtestcase.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Configuration
class RegistrationHandler {

    // TODO: create something like BehaviorSubject in RxJava to store the current state of registration
    val registrationState: Flux<Boolean>? = null

    @Bean
    fun route() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/register/user", this@RegistrationHandler.handleRegisterPerson)
        }
    }

    private val handleRegisterPerson: (ServerRequest) -> Mono<out ServerResponse> =
            { request: ServerRequest ->
                val registerRequest = request.bodyToMono(RegisterUserRequest::class.java)

                ok().build { subscriber ->
                    registerRequest
                            .flatMap { r: RegisterUserRequest -> validateRegistrationForm(r) }
                            .subscribe {
                                println("Got some register request.")
                                subscriber.onComplete()
                            }
                }
            }

    private fun validateRegistrationForm(it: RegisterUserRequest): Mono<Unit> {
        // TODO: validate input
        return Mono.just(Unit)
    }
}
