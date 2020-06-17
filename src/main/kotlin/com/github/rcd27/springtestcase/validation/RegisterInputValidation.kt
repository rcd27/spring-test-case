package com.github.rcd27.springtestcase.validation

import arrow.core.Invalid
import arrow.core.NonEmptyList
import arrow.core.None
import arrow.core.Some
import arrow.core.Valid
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

        val errorList = mutableListOf<RegisterUserValidationError>()

        val regex = "^([a-zA-Z0-9_\\-.]+)@(([a-zA-Z0-9_\\-]+)\\.){1,2}([a-zA-Z]{2,5})\$".toRegex()
        if (input.email?.matches(regex) == false) {
            errorList.add(RegisterUserValidationError("Invalid email"))
        }

        if (input.firstName.isNullOrBlank()) {
            errorList.add(RegisterUserValidationError("First name is blank"))
        }

        if (input.lastName.isNullOrBlank()) {
            errorList.add(RegisterUserValidationError("Last name name is blank"))
        }

        if (input.dateOfBirth.isNullOrBlank()) {
            // TODO: add date parsing
            errorList.add(RegisterUserValidationError("Please enter your birth date"))
        }

        // let information about cities be null
        return when (val resultErrorList = NonEmptyList.fromList(errorList)) {
            is Some -> Mono.just(Invalid(resultErrorList.t))
            is None -> Mono.just(Valid(input))
        }
    }
}

data class RegisterUserValidationError(val message: String)
