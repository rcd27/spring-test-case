import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract
import org.springframework.cloud.contract.verifier.util.ContentType

contract {
    name = "Approver"
    description = "Should approve valid request"

    request {
        url = url("/api/v1/approve")
        method = POST

        headers {
            header("Content-Type", ContentType.JSON.mimeType)
        }

        body = body(
            mapOf(
                "verificationId" to anyNonBlankString,
                "firstName" to "Stanislav",
                "lastName" to "Zemlyakov",
                "email" to "redtom@yandex.ru",
                "dateOfBirth" to "01.08.1989",
                "habitatCity" to "Innopolis",
                "registrationCity" to "Saint-Petersburg"
            )
        )

        bodyMatchers {
            jsonPath("$.firstName", byRegex(".*[^1-9]")) // any char without numbers
        }
    }

    response {
        status = OK

        headers {
            header("Content-Type", ContentType.JSON.mimeType)
        }

        body = body(
            mapOf(
                // FIXME: Deserialize json fromReponse()
                "request" to mapOf(
                    "verificationId" to anyNonBlankString,
                    "firstName" to "Stanislav",
                    "lastName" to "Zemlyakov",
                    "email" to "redtom@yandex.ru",
                    "dateOfBirth" to "01.08.1989",
                    "habitatCity" to "Innopolis",
                    "registrationCity" to "Saint-Petersburg"
                ),
                "status" to "Approved"
            )
        )
    }
}