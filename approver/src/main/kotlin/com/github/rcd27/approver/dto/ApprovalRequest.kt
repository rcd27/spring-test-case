package com.github.rcd27.approver.dto

data class ApprovalRequest(
    val verificationId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val habitatCity: String?,
    val registrationCity: String?
)