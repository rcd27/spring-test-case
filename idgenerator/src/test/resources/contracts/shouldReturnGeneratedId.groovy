import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.GET

Contract.make {
    description("Should return generated id")

    request {
        url("/api/v1/id/generate")
        method(GET.methodName)
    }

    response {
        status(200)
        body("some-unique-as-hell-id")
    }
}