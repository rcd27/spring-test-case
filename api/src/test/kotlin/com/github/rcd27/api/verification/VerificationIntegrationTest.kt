package com.github.rcd27.api.verification

import com.github.rcd27.api.approval.data.ApprovalRepository
import com.github.rcd27.api.approval.data.ApprovalRequest
import com.github.rcd27.api.approval.data.ApprovalResponse
import com.github.rcd27.api.approval.data.ApprovalStatus
import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.entities.persist.VerificationProcess.VerificationStatus.*
import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import com.google.common.truth.Truth
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VerificationIntegrationTest(@Autowired private val webClient: WebTestClient) {

    @SpykBean
    lateinit var idGenerationRepository: IdGenerationRepository

    @SpykBean
    lateinit var approvalRepository: ApprovalRepository

    private val validRequest = VerificationRequest(
            "Stanislav",
            "Zemlyakov",
            "redtom@yandex.ru",
            "01.08.1989",
            "Innopolis",
            "Saint-Petersburg"
    )

    @Suppress("ReactiveStreamsUnusedPublisher")
    @BeforeEach
    fun setUp() {
        every { idGenerationRepository.generateUniqueId() } returns Mono.just("very-unique-shit")
        every { approvalRepository.sendForApproval(any()) } returns Mono.just(
                ApprovalResponse(
                        request = ApprovalRequest.fromVerificationRequest("very-unique-shit", validRequest),
                        status = ApprovalStatus.Approved
                )
        )
    }

    @Test
    fun `should return id of a verification process`() {
        val idGivenForVerificationRequest = webClient.post()
                .uri("/api/v1/verification/verify")
                .body(Mono.just(validRequest), VerificationRequest::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult().responseBody

        Truth.assertThat(idGivenForVerificationRequest).isNotEmpty()
    }

    @Test
    fun `should return bad request for verification process which doesn't exist`() {
        webClient.get()
                .uri("/api/v1/verification/status/123")
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `should return status for previously created verification process`() {
        val idGivenForVerificationRequest = webClient.post()
                .uri("/api/v1/verification/verify")
                .body(Mono.just(validRequest), VerificationRequest::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult().responseBody

        val body = webClient.get()
                .uri("/api/v1/verification/status/$idGivenForVerificationRequest")
                .exchange()
                .expectStatus().isOk
                .expectBody<String>()
                .returnResult()
                .responseBody

        Truth.assertThat(body).isAnyOf(APPROVED.toString(), DECLINED.toString(), IN_PROGRESS.toString())
    }
}