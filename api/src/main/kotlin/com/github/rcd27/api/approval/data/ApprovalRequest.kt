package com.github.rcd27.api.approval.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.rcd27.api.entities.dto.VerificationRequest

data class ApprovalRequest(
    @JsonProperty("verificationId") val verificationId: String,
    @JsonProperty("firstName") val firstName: String,
    @JsonProperty("lastName") val lastName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("dateOfBirth") val dateOfBirth: String,
    @JsonProperty("habitatCity") val habitatCity: String?,
    @JsonProperty("registrationCity") val registrationCity: String?
) {
    companion object {
        @JvmStatic
        fun fromVerificationRequest(id: String, request: VerificationRequest) =
            ApprovalRequest(
                verificationId = id,
                firstName = request.firstName,
                registrationCity = request.registrationCity,
                habitatCity = request.habitatCity,
                email = request.email,
                dateOfBirth = request.dateOfBirth,
                lastName = request.lastName
            )
    }
}
