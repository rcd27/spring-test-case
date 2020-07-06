package com.github.rcd27.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
open class ApiApplication {

    // FIXME: should it be here or in @Configuration?
    // TODO: we can do bunch of stuff to builder in order to add some extra functionality to `WebClient`
    @Bean
    open fun provideWebClient(): WebClient = WebClient.create()

}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
