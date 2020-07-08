package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import com.google.common.truth.Truth
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
class VerificationIntegrationTest(@Autowired private val webClient: WebTestClient) {

    @SpykBean
    lateinit var idGenerationRepository: IdGenerationRepository

    @Suppress("ReactiveStreamsUnusedPublisher")
    @BeforeEach
    fun setUp() {
        every { idGenerationRepository.generateUniqueId() } returns Mono.just("very-unique-shit")
    }

    private val validRequest = VerificationRequest(
            "Stanislav",
            "Zemlyakov",
            "redtom@yandex.ru",
            "01.08.1989",
            "Innopolis",
            "Saint-Petersburg"
    )

    @Test
    fun `should send for verification and return its id`() {
        // TODO: either check that verification is sent, or rename the test case

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

        webClient.get()
                .uri("/api/v1/verification/status/$idGivenForVerificationRequest")
                .exchange()
                .expectStatus().isOk
    }

    // TODO: test when not passing {id} for verificationStatus
    // TODO: test when other unhandled exception is thrown (if it canbe thrown in that chain of a VerificationHandler)
}