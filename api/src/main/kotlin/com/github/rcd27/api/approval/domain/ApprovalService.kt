package com.github.rcd27.api.approval.domain

import com.github.rcd27.api.approval.data.ApprovalRepository
import com.github.rcd27.api.approval.data.ApprovalRequest
import com.github.rcd27.api.approval.data.ApprovalResponse
import com.github.rcd27.api.entities.dto.VerificationRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ApprovalService(private val approvalRepository: ApprovalRepository) {

    fun sendForApproval(id: String, request: VerificationRequest): Mono<ApprovalResponse> =
        approvalRepository.sendForApproval(
            ApprovalRequest.fromVerificationRequest(id, request)
        )
}