package com.github.rcd27.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(TestController::class)
class TestControllerTest {

  @Autowired
  lateinit var client: WebTestClient

  @Test
  fun test() {
    client.get().uri("/test")
        .exchange()
        .expectStatus().isOk
        // ATTENTION: use KotlinBodySpec from Kotlin, otherwise be ready for NPE from Java code <3
        .expectBody<String>().isEqualTo("Fine, fine, you got it")
  }
}