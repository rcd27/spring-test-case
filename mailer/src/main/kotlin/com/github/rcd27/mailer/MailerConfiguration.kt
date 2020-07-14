package com.github.rcd27.mailer

import com.github.rcd27.mailer.dto.MailerRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.EmitterProcessor

@Configuration
open class MailerConfiguration {

    @Bean
    open fun provideMailerRequestProcessor(): EmitterProcessor<MailerRequest> = EmitterProcessor.create()

}