package com.github.rcd27.api.integration

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.google.common.truth.Truth
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VerificationTest(@Autowired val webClient: WebTestClient) {

  @Test
  fun `should send for verification and return its id`() {
    val request = VerificationRequest(
        "Stanislav",
        "Zemlyakov",
        "redtom@yandex.ru",
        "01.08.1989",
        "Innopolis",
        "Saint-Petersburg"
    )

    val idGivenForVerificationRequest = webClient.post()
        .uri("/api/v1/verification/verify")
        .body(Mono.just(request), VerificationRequest::class.java)
        .exchange()
        .expectStatus().isOk
        .expectBody(String::class.java)
        .returnResult().responseBody

    Truth.assertThat(idGivenForVerificationRequest).isNotEmpty()
  }

//  @Test
//  fun `should return current status for verification process`() {
//    webClient.get()
//        .uri("/api/v1/verification/status/123")
//        .exchange()
//        .expectStatus().isOk
//  }
}