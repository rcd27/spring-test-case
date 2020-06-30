package com.github.rcd27.api.verification

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.rcd27.api.idgeneration.domain.IdGenerationUseCase
import com.github.rcd27.api.validation.domain.ValidationUseCase
import com.github.rcd27.api.verification.domain.VerificationStatusUseCase
import com.github.rcd27.api.verification.domain.VerificationUseCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

// FIXME: самой по себе логики бизнесовой в контроллере нет, что тестить?
@WebFluxTest(controllers = [VerificationController::class])
internal class VerificationControllerTest {

  @MockBean
  lateinit var verificationUseCase: VerificationUseCase

  @MockBean
  lateinit var verificationStatusUseCase: VerificationStatusUseCase

  @MockBean
  lateinit var idGenerationUseCase: IdGenerationUseCase

  @MockBean
  lateinit var validationUseCase: ValidationUseCase


  @Autowired
  lateinit var webClient: WebTestClient

  @Test
  fun main() {
    val request = VerificationRequest(
      "Stanislav",
      "Zemlyakov",
      "redtom@yandex.ru",
      "01.08.1989",
      "Innopolis",
      "Saint-Petersburg"

      // FIXME: implement test case or get rid
    )
  }
}