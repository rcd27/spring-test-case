package com.github.rcd27.api.approval.data

import com.github.rcd27.api.entities.dto.VerificationRequest

data class ApprovalRequest(
    val verificationId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val habitatCity: String?,
    val registrationCity: String?
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
