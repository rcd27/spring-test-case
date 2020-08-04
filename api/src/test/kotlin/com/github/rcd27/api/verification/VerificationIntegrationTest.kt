package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.entities.persist.VerificationProcess.VerificationStatus.*
import com.google.common.truth.Truth
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@AutoConfigureStubRunner(
    failOnNoStubs = true,
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    generateStubs = true,
    ids = [
        "com.github.rcd27.idgenerator:idgenerator:+:8081",
        "com.github.rcd27.approver:approver:+:8082"
    ]
)
@SpringBootTest(
    properties = [
        "api.idgeneratorUrl=http://localhost:8081/",
        "api.approverUrl=http://localhost:8082/"
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class VerificationIntegrationTest(@Autowired private val webClient: WebTestClient) {

    private val validRequest = VerificationRequest(
        "Stanislav",
        "Zemlyakov",
        "redtom@yandex.ru",
        "01.08.1989",
        "Innopolis",
        "Saint-Petersburg"
    )

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