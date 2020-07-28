package com.github.rcd27.idgenerator

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdGeneratorIntegrationTest(@Autowired val webClient: WebTestClient) {

    @Test
    fun `should return different id`() {

        val firstResponse = webClient.get()
                .uri("api/v1/id/generate")
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult().responseBody

        val secondResponse = webClient.get()
                .uri("api/v1/id/generate")
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult().responseBody

        Truth.assertThat(firstResponse).doesNotMatch(secondResponse)
    }

    @Test
    fun `should return id without parenthesis`() {
        val id = webClient.get()
                .uri("api/v1/id/generate")
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult().responseBody

        Truth.assertThat(id).doesNotContain("\"")
    }
}