package com.github.rcd27.api.verification.data

import com.github.rcd27.api.entities.persist.VerificationProcess
import com.github.rcd27.api.entities.persist.VerificationProcess.VerificationStatus.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@DataMongoTest
class VerificationRepositoryTest {

    @Autowired
    lateinit var verificationProcessRepository: VerificationProcessRepository

    /**
     *  We don't have to test repository if there are no custom queries.
     *  But since I faced a kotlinCompile error, when trying to add such a method to `VerificationProcessRepository`,
     *  let it be a simple useless test case.
     */
    @Test
    fun `simple useless test`() {
        val getById = verificationProcessRepository.deleteAll()
            .thenMany(
                Flux.just(
                    "1" to IN_PROGRESS,
                    "2" to DECLINED,
                    "3" to IN_PROGRESS,
                    "4" to APPROVED
                )
                    .flatMap { (id, status) ->
                        verificationProcessRepository.save(VerificationProcess(id, status))
                    }
            )
            .thenMany(verificationProcessRepository.findById("1"))

        StepVerifier.create(getById)
            .expectNextCount(1)
            .verifyComplete()
    }
}