package com.github.rcd27.approver

import com.github.rcd27.approver.domain.Invalid
import com.github.rcd27.approver.domain.RequestValidator
import com.github.rcd27.approver.domain.Valid
import com.github.rcd27.approver.dto.ApprovalRequest
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.kafka.test.rule.EmbeddedKafkaRule
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Suppress("ReactiveStreamsUnusedPublisher")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApproverIntegrationTest(@Autowired private val webClient: WebTestClient) {

    @Rule
    @JvmField
    val rule: EmbeddedKafkaRule = EmbeddedKafkaRule(1)
            .kafkaPorts(1092)

    @SpykBean
    lateinit var validator: RequestValidator

    private val validApprovalRequest = ApprovalRequest(
            "some-unique-id",
            "Stanislav",
            "Zemlyakov",
            "redtom@yandex.ru",
            "01.08.1989",
            "Innopolis",
            "Saint-Petersburg"
    )

    @Test
    fun `should send Kafka message if input is valid`() {
        every { validator.validate(any()) } returns Mono.just(Valid)

        rule.before()

        webClient.post()
                .uri("/api/v1/approve")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(validApprovalRequest), ApprovalRequest::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("\$.status").isEqualTo("Approved")

        rule.after()

        // FIXME: Kafka still can't connect to embedded server
        // TODO: test Kafka: assert it has sent a query
    }

    @Test
    fun `should NOT send Kafka message if input is INVALID`() {
        every { validator.validate(any()) } returns Mono.just(Invalid)

        webClient.post()
                .uri("/api/v1/approve")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(validApprovalRequest), ApprovalRequest::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("\$.status").isEqualTo("Denied")

        // TODO: test Kafka: assert it has NOT sent a query
    }
}