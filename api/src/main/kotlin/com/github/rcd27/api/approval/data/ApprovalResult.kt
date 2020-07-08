package com.github.rcd27.api.approval.data

import com.fasterxml.jackson.annotation.JsonProperty

data class ApprovalResult(
    @JsonProperty("request") val request: ApprovalRequest,
    // FIXME: error while parsing sealed class values. Was found in `ApproverIntegrationTest`
    val result: String
)