package com.github.rcd27.springtestcase.validation

import arrow.core.NonEmptyList
import arrow.core.Validated
import com.github.rcd27.springtestcase.rest.RegisterUserRequest
import reactor.core.publisher.Mono

object RegisterInputValidation {

    /**
     * For validation we use [Validated] monad which returns either `valid` value on ther right
     * or invalid value on the left. All invalid values are collected to [NonEmptyList], so
     * if [RegisterUserRequest] is invalid, at least one invalid value will be present
     */
    fun validate(input: RegisterUserRequest):
            Mono<Validated<NonEmptyList<RegisterUserValidationError>, RegisterUserRequest>> {
        TODO("")
    }
}

data class RegisterUserValidationError(val message: String)
