import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.GET

// TODO: change for Kotlin DSL
// see: https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html
Contract.make {
    description("Should return generated id")

    request {
        url("http://localhost:8081/api/v1/id/generate")
        method(GET.methodName)
    }

    response {
        status(200)
        body(anyNonBlankString())
    }
}