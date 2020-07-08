package com.github.rcd27.api.approval.data

import com.github.rcd27.api.entities.dto.VerificationRequest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import reactor.test.StepVerifier

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class ApprovalRepositoryTest(@Autowired private val approvalRepository: ApprovalRepository) {

    private val validRequest = VerificationRequest(
        "Stanislav",
        "Zemlyakov",
        "redtom@yandex.ru",
        "01.08.1989",
        "Innopolis",
        "Saint-Petersburg"
    )

    @Test
    fun `should deserialize JSON to ApprovalResponse`() {
        stubFor(
            post(urlEqualTo("/api/v1/approve"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json").withBody(
                            """
                            {
                                "request": {
                                    "verificationId": "some-inuque-shit",
                                    "firstName": "Stanislav",
                                    "lastName": "Zemlyakov",
                                    "email": "redtom@yandex.ru",
                                    "dateOfBirth": "01.08.1989",
                                    "habitatCity": "Saint-Petersburg",
                                    "registrationCity": "s"
                                },
                                "result": "Approved"
                            }
                        """.trimIndent()
                        )
                )
        )

        val sendForApproval = approvalRepository.sendForApproval(
            ApprovalRequest.fromVerificationRequest(
                "some-inuque-shit",
                validRequest
            )
        )

        StepVerifier.create(sendForApproval)
            .expectNextCount(1)
            .verifyComplete()
    }

}