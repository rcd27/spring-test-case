package com.github.rcd27.api.idgeneration.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

// TODO: this dancing around interfaces can be skipped with use of MockK and ninja stuff
interface IdGenerationRepository {
    fun generateUniqueId(): Mono<String>
}

@Component
class IdGenerationRepositoryImpl : IdGenerationRepository {

    @Autowired
    lateinit var webClient: WebClient

    override fun generateUniqueId(): Mono<String> =
        webClient.get()
            .uri("http://localhost:8080/api/v1/id/generate")
            .retrieve()
            .bodyToMono(String::class.java)
}