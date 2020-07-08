package com.github.rcd27.api.entities.persist

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class VerificationProcess(
        val id: String,
        val status: VerificationStatus
) {
    enum class VerificationStatus {
        IN_PROGRESS, DECLINED, APPROVED
    }
}