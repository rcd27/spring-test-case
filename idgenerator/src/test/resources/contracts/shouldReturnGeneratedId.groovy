package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.GET

Contract.make {
    description("Should return generated id")

    request {
        url("/api/v1/id/generate")
        method(GET.methodName)
    }

    response {
        status(HttpStatus.OK.value())
        body("some-unique-as-hell-id")
    }
}