package com.github.rcd27.api.approval.data

data class ApprovalResponse(
    val request: ApprovalRequest,
    val status: ApprovalStatus
)

enum class ApprovalStatus {
  Approved, Denied
}