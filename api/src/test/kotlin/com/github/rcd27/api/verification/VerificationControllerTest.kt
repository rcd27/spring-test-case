package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [VerificationController::class])
internal class VerificationControllerTest {

  @MockBean lateinit var verificationUseCase: VerificationUseCase
  @MockBean lateinit var idGenerationUseCase: IdGenerationUseCase
  @MockBean lateinit var verificationStatusUseCase: VerificationStatusUseCase

  @Autowired lateinit var webClient: WebTestClient

  @Test
  fun `verify valid input`() {
    val request = VerificationRequest(
        "Stanislav",
        "Zemlyakov",
        "redtom@yandex.ru",
        "01.08.1989",
        "Innopolis",
        "Saint-Petersburg"
    )

    val testId = "test_id"

    Mockito.`when`(idGenerationUseCase.getUniqueId())
        .thenReturn(Mono.just(testId))

    Mockito.`when`(verificationUseCase.verify(testId, Mono.just(request)))
        .thenReturn(Mono.just(testId))

    webClient.post()
        .uri("/verify")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(request), VerificationRequest::class.java)
        .exchange()
        .expectStatus().isOk
        // ATTENTION: use KotlinBodySpec from Kotlin, otherwise be ready for NPE from Java code <3
        .expectBody<String>().isEqualTo(testId)
  }

  @Test
  fun `check status for existing id`() {
    val fakeId = "123"

    Mockito.`when`(verificationStatusUseCase.checkVerificationStatus(fakeId))
        .thenReturn(Mono.just(VerificationProcess.VerificationStatus.IN_PROGRESS.toString()))

    webClient.get()
        .uri("/status/$fakeId")
        .exchange()
        .expectStatus().isOk
        .expectBody<String>().isEqualTo("IN_PROGRESS")
  }
}