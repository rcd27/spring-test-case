package com.github.rcd27.api.idgeneration.data

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
@RunWith(SpringRunner::class)
class IdGenerationRepositoryTest {

    @Autowired
    lateinit var idGenerationRepository: IdGenerationRepository

    @Test
    fun `should go to end-point and retrieve id`() {
        val generateUniqueId: Mono<String> = idGenerationRepository.generateUniqueId()
        StepVerifier
            .create(generateUniqueId)
            .expectNextMatches { it.isNotEmpty() }
            .verifyComplete()
    }
}