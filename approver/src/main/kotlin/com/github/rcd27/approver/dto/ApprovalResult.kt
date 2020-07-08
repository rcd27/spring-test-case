package com.github.rcd27.approver.dto

data class ApprovalResult(
    val request: ApprovalRequest,
    // FIXME: error while parsing sealed class values. Was found in `ApproverIntegrationTest`
    val result: String
)