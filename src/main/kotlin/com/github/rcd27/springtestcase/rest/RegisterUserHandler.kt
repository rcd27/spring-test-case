package com.github.rcd27.springtestcase.rest

import arrow.core.Invalid
import arrow.core.NonEmptyList
import arrow.core.Valid
import com.github.rcd27.springtestcase.SpringTestCaseApplication
import com.github.rcd27.springtestcase.db.User
import com.github.rcd27.springtestcase.db.UserRepository
import com.github.rcd27.springtestcase.validation.RegisterInputValidation
import com.github.rcd27.springtestcase.validation.RegisterUserValidationError
import org.springframework.amqp.rabbit.core.RabbitTemplate
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
class RegistrationHandler constructor(
        private val userRepository: UserRepository,
        private val rabbitTemplate: RabbitTemplate
) {

    // TODO: create something like BehaviorSubject in RxJava to store the current state of registration
    val registrationState: Flux<RegistrationState>? = null

    @Bean fun route() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/register/user", this@RegistrationHandler.handleRegisterPerson)
        }
    }

    private val handleRegisterPerson: (ServerRequest) -> Mono<out ServerResponse> =
            { request: ServerRequest ->
                val registerRequest = request.bodyToMono(RegisterUserRequest::class.java)

                registerRequest
                        .flatMap { r: RegisterUserRequest -> RegisterInputValidation.validate(r) }
                        .flatMap { validationResult ->
                            when (validationResult) {
                                is Valid -> {
                                    val value = validationResult.a
                                    userRepository.save(
                                            User(
                                                    firstName = value.firstName,
                                                    lastName = value.lastName,
                                                    email = value.email,
                                                    dateOfBirth = value.dateOfBirth,
                                                    registrationCity = value.registrationCity,
                                                    habitatCity = value.habitatCity
                                            )
                                    )
                                            .flatMap { savedUser ->
                                                Mono.just(ValidationOk(savedUser))
                                            }
                                }
                                is Invalid -> Mono.just(ValidationError(validationResult.e))
                            }
                        }
                        .flatMap {
                            when (it) {
                                is ValidationError -> Mono.just(it)
                                else -> Mono.fromCallable {
                                    rabbitTemplate.convertAndSend(
                                            SpringTestCaseApplication.topicExchangeName,
                                            "${SpringTestCaseApplication.messageRoutingKey}$it",
                                            "Saved user to db and sent message: $it"
                                    )
                                }
                                        .map<RegistrationState> { RabbitMessageSent }
                            }
                        }
                        .flatMap { state ->
                            when (state) {
                                is ValidationError -> {
                                    val errorMessage =
                                            state.validationErrors.foldLeft("Validation errors:",
                                                    { s: String, e: RegisterUserValidationError ->
                                                        "$s\n${e.message}"
                                                    })
                                    ServerResponse.badRequest().bodyValue(errorMessage)
                                }
                                else -> ok().build()
                            }
                        }
            }
}

sealed class RegistrationState
data class ValidationOk(val user: User) : RegistrationState()
data class ValidationError(val validationErrors: NonEmptyList<RegisterUserValidationError>) : RegistrationState()
object RabbitMessageSent : RegistrationState()
