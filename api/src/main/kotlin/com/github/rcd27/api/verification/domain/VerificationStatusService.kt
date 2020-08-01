package com.github.rcd27.api.verification.domain

import com.github.rcd27.api.approval.data.ApprovalResponse
import com.github.rcd27.api.approval.data.ApprovalStatus
import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.verification.data.VerificationProcessRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class VerificationStatusService(private val verificationProcessRepository: VerificationProcessRepository) {

  fun checkVerificationStatus(id: String): Mono<String> =
      verificationProcessRepository.findById(id)
          .map { it.status.toString() }
          .switchIfEmpty {
            Mono.error(NoProcessForIdException(id))
          }

  fun saveApprovalResult(approvalResponse: ApprovalResponse): Mono<VerificationProcess> {

    val status = when (approvalResponse.status) {
      ApprovalStatus.Approved -> VerificationProcess.VerificationStatus.APPROVED
      ApprovalStatus.Denied -> VerificationProcess.VerificationStatus.DECLINED
    }

    return verificationProcessRepository.save(
        VerificationProcess(
            approvalResponse.request.verificationId,
            status
        )
    )
  }
}

class NoProcessForIdException(val id: String) : Throwable("No process for such id: $id")