package com.github.rcd27.mailer

import com.github.rcd27.mailer.MailerConfiguration.Companion.MAILER_QUEUE
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class MailerListener {

    @RabbitListener(queues = [MAILER_QUEUE])
    fun listen(input: String) {
        println(input)
    }
}