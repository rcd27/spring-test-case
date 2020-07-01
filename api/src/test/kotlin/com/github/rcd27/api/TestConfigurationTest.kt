package com.github.rcd27.api

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class TestConfigurationTest {

  private val context = AnnotationConfigApplicationContext().apply {
    register(TestConfiguration::class.java)
    refresh()
  }

  private val client: WebTestClient = WebTestClient.bindToApplicationContext(context).build()

  @Test
  fun test() {
    client.get().uri("/test")
        .exchange()
        .expectStatus().isOk
        // ATTENTION: use KotlinBodySpec from Kotlin, otherwise be ready for NPE from Java code <3
        .expectBody<String>().isEqualTo("Fine, fine, you got it")
  }
}