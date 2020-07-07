package com.github.rcd27.api.idgeneration.data

import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.Before
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
@AutoConfigureStubRunner(
    ids = ["com.github.rcd27:idgenerator:+:8081"],
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class IdGenerationRepositoryTest {

    @Autowired
    lateinit var idGenerationRepository: IdGenerationRepository

    @Before
    fun setUp() {
        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/api/v1/id/generate"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(
                            HttpHeaders.CONTENT_TYPE
                        )
                        .withBody("some-unique-as-hell-id")
                )
        )
    }

    @Test
    fun `should go to end-point and retrieve id`() {
        val generateUniqueId: Mono<String> = idGenerationRepository.generateUniqueId()
        StepVerifier
            .create(generateUniqueId)
            .expectNextMatches { it.isNotEmpty() }
            .verifyComplete()
    }
}