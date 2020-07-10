package com.github.rcd27.mailer

import com.github.rcd27.mailer.dto.MailerRequest
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.EmitterProcessor

@Configuration
@EnableRabbit
open class MailerConfiguration {

    @Bean
    open fun provideMailerRequestProcessor(): EmitterProcessor<MailerRequest> = EmitterProcessor.create()

    // TODO: see @EnableRabbit annotation and implement

    @Bean
    open fun myQueue(): Queue {
        return Queue(MAILER_QUEUE)
    }

    companion object {
        const val MAILER_QUEUE = "mailerQueue"
    }
}