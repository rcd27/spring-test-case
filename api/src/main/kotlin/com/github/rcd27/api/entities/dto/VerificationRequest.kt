package com.github.rcd27.api.entities.dto

// FIXME: this should be in `common` module. Will be fixed in `common-module` branch
data class VerificationRequest(
        val firstName: String,
        val lastName: String,
        val email: String,
        val dateOfBirth: String,
        val habitatCity: String?,
        val registrationCity: String?
)