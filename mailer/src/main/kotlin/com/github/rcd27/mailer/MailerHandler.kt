package com.github.rcd27.mailer

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.rcd27.mailer.MailerConfiguration.Companion.MAILER_QUEUE
import com.github.rcd27.mailer.dto.MailerRequest
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import reactor.core.publisher.EmitterProcessor

/**
 * This class mimics the other `handlers`, like it is the same `entry-point` to the Mailer app, no
 * matter that it uses RabbitMQ. It is still an entry point. We can rename it and implement MailerService logic here,
 * but there will be too much information to display: queue name, json mapping, mail logic etc.
 *
 * Treat this as (String) -> MailerRequest
 */
@Component
class MailerHandler(private val messageListener: EmitterProcessor<MailerRequest>) {

    @RabbitListener(queues = [MAILER_QUEUE])
    fun listen(input: String) {
        val objectMapper = jacksonObjectMapper()
        try {
            val mailerRequest: MailerRequest = objectMapper.readValue(input)
            messageListener.onNext(mailerRequest)
        } catch (jpe: JsonParseException) {
            // TODO: implement
        } catch (jme: JsonMappingException) {
            // TODO: implement
        }
    }
}