package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.entities.persist.VerificationProcess.VerificationStatus.IN_PROGRESS
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@WebFluxTest(VerificationStatusConfiguration::class)
class VerificationStatusConfigurationTest {

  @Autowired
  lateinit var webClient: WebTestClient

  @MockBean
  lateinit var verificationStatusUseCase: VerificationStatusUseCase

  @Test
  fun verificationStatusRoute() {
    Mockito.`when`(verificationStatusUseCase.checkVerificationStatus())
      .thenReturn(
        Mono.just(
          VerificationProcess("123", IN_PROGRESS)
        )
      )

    webClient.get()
      .uri("/status")
      .exchange()
      .expectStatus().isOk
  }
}