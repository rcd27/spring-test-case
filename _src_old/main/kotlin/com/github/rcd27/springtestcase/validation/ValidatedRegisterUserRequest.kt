package com.github.rcd27.springtestcase.validation

data class ValidatedRegisterUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val habitatCity: String?,
    val registrationCity: String?
)
