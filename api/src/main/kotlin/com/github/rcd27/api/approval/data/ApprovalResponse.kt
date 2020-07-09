package com.github.rcd27.api.approval.data

data class ApprovalResponse(
    val request: ApprovalRequest,
    // FIXME: error while parsing sealed class values. Was found in `ApproverIntegrationTest`
    val result: String
)