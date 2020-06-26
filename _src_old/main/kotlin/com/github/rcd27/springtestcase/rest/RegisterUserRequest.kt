package com.github.rcd27.springtestcase.rest

data class RegisterUserRequest(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val dateOfBirth: String?,
    val habitatCity: String?,
    val registrationCity: String?
)
