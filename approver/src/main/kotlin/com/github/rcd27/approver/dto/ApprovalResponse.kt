package com.github.rcd27.approver.dto

data class ApprovalResponse(
    val request: ApprovalRequest,
    val status: ApprovalStatus
)

enum class ApprovalStatus {
  Approved, Denied
}