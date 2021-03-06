package com.github.rcd27.api.idgeneration.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class IdGenerationRepository(
    private val webClient: WebClient,
    @Value("\${api.idgeneratorUrl:http://idgenerator:8081}") private val idGeneratorUrl: String
) {

  fun generateUniqueId(): Mono<String> =
      webClient.get()
          .uri("$idGeneratorUrl/api/v1/id/generate")
          .retrieve()
          .bodyToMono(String::class.java)
}