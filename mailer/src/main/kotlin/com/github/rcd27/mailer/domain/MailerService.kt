package com.github.rcd27.mailer.domain

import com.github.rcd27.mailer.dto.MailerRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.EmitterProcessor
import javax.annotation.PostConstruct

@Service
class MailerService(private val mailerRequestProcessor: EmitterProcessor<MailerRequest>) {

    @PostConstruct
    fun subscribeForRequests() {
        mailerRequestProcessor.subscribe(
            { println("MailerService received: $it") },
            {}
        )
    }
}
