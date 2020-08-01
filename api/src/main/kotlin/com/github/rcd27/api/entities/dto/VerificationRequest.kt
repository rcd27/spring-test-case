package com.github.rcd27.api.entities.dto

data class VerificationRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val habitatCity: String?,
    val registrationCity: String?
)