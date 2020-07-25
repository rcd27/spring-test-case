package com.github.rcd27.api.entities.persist

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@DataMongoTest
class VerificationProcessTest(@Autowired private val reactiveMongoTemplate: ReactiveMongoTemplate) {

    @Test
    fun `persist test`() {
        val verificationProcess = VerificationProcess("any_uuid", VerificationProcess.VerificationStatus.IN_PROGRESS)
        val savePublisher: Mono<VerificationProcess> = reactiveMongoTemplate.save(verificationProcess)

        StepVerifier.create(savePublisher)
                .expectNextMatches { it.id.isNotEmpty() && it.status == VerificationProcess.VerificationStatus.IN_PROGRESS }
                .verifyComplete()

        val findById = reactiveMongoTemplate.findById("any_uuid", VerificationProcess::class.java)
        StepVerifier.create(findById)
                .expectNextCount(1)
                .verifyComplete()
    }
}