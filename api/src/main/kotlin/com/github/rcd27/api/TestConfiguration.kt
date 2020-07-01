package com.github.rcd27.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
open class TestConfiguration {

  @Bean
  open fun provideTestController(): TestController {

    return TestController()
  }
}