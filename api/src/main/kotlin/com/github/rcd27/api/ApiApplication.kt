package com.github.rcd27.api

import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import reactor.core.publisher.Mono

@SpringBootApplication
open class ApiApplication {

    @Bean
    open fun provideIdGenerationRepository(): IdGenerationRepository = object : IdGenerationRepository {
        override fun generateUniqueId(): Mono<String> {
            // TODO go to generateId url of IdGenerator and get uniqueID
            return Mono.just("hardcoded_unique_id")
        }
    }

    @Bean
    @Qualifier("verificationRequest")
    open fun provideVerificationRequestValidator(): Validator = object : Validator {

        override fun validate(target: Any, errors: Errors) {
            TODO("implement")
        }

        override fun supports(clazz: Class<*>): Boolean {
            TODO("implement")
        }

    }
}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
