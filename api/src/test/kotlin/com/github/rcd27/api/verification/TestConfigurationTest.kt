package com.github.rcd27.api.verification

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@WebFluxTest(TestConfiguration::class)
internal class TestConfigurationTest {

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun check() {
        client.get()
            .uri("/test")
            .exchange()
            .expectStatus().isOk
    }
}