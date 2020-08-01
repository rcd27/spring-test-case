import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    name = "some name"
    description = "Should return generated id"

    request {
        url = url("/api/v1/id/generate")
        method = GET
    }

    response {
        status = OK
        body = body(anyNonBlankString)
    }
}