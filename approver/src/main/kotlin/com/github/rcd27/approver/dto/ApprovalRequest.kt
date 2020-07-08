package com.github.rcd27.approver.dto

// FIXME: this should be in `common` module. Will be fixed in `common-module` branch
data class ApprovalRequest(
        val verificationId: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val dateOfBirth: String,
        val habitatCity: String?,
        val registrationCity: String?
)