package com.github.rcd27.api

import com.github.rcd27.api.idgeneration.data.IdGenerationRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
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
}

fun main(args: Array<String>) {
  runApplication<ApiApplication>(*args)
}
