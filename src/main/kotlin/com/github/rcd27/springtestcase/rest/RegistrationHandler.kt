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
    val registrationState: Flux<Boolean>? = null

    @Bean fun route() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/register/user", this@RegistrationHandler.handleRegisterPerson)
        }
    }

    private val handleRegisterPerson: (ServerRequest) -> Mono<out ServerResponse> =
            { request: ServerRequest ->
                val registerRequest = request.bodyToMono(RegisterUserRequest::class.java)

                ok().build { subscriber ->
                    registerRequest
                            .flatMap { r: RegisterUserRequest -> RegisterInputValidation.validate(r) }
                            .flatMap { validationResult ->
                                when (validationResult) {
                                    is Valid -> {
                                        val value = validationResult.a
                                        userRepository.save(
                                                User(
                                                        // FIXME: unwrap from "" - dirty hack of nullability
                                                        firstName = "${value.firstName}",
                                                        lastName = "${value.lastName}",
                                                        email = "${value.email}",
                                                        dateOfBirth = "${value.dateOfBirth}",
                                                        registrationCity = value.registrationCity,
                                                        habitatCity = value.habitatCity
                                                )
                                        )
                                    }
                                    is Invalid -> Mono.error(ValidationException(validationResult.e))
                                }
                            }
                            .flatMap {
                                Mono.fromCallable {
                                    rabbitTemplate.convertAndSend(
                                            SpringTestCaseApplication.topicExchangeName,
                                            "${SpringTestCaseApplication.messageRoutingKey}baz",
                                            "Saved user to db and sent message: $it"
                                    )
                                }
                            }
                            .subscribe(
                                    {
                                        subscriber.onComplete()
                                    },
                                    {
                                        subscriber.onError(it)
                                    }
                            )
                }
            }
}

data class ValidationException(val validationError: NonEmptyList<RegisterUserValidationError>) : Throwable()
