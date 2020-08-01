package com.github.rcd27.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class ApiConfiguration {

  @Bean
  open fun provideWebClient(): WebClient = WebClient.create()
}