package com.github.rcd27.approver.dto

data class Approval(
    val requestId: String,
    val request: ApprovalRequest,
    // FIXME: error while parsing sealed class values. Was found in `ApproverIntegrationTest`
    val result: String
)

sealed class ApprovalResult
object Approved : ApprovalResult()
object Denied : ApprovalResult()