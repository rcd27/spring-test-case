package com.github.rcd27.api.idgeneration.data

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

// TODO: see https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html#features-wiremock
// https://github.com/spring-cloud/spring-cloud-contract/tree/master/samples/standalone/contracts
@Component
class IdGenerationRepository(private val webClient: WebClient) {

    fun generateUniqueId(): Mono<String> =
            webClient.get()
                    .uri("http://idgenerator:8081/api/v1/id/generate")
                    .retrieve()
                    .bodyToMono(String::class.java)
}