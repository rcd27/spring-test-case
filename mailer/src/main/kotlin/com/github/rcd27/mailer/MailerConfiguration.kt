package com.github.rcd27.mailer

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
open class MailerConfiguration {

    @Bean
    open fun listener(): MailerListener = MailerListener()

    // TODO: see @EnableRabbit annotation and implement

    @Bean
    open fun myQueue(): Queue {
        return Queue(MAILER_QUEUE)
    }

    companion object {
        const val MAILER_QUEUE = "mailerQueue"
    }
}