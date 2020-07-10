package com.github.rcd27.mailer.dto

data class MailerRequest(
    val id: String,
    val status: String
)

enum class ApprovalStatus {
    Approved, Denied
}
