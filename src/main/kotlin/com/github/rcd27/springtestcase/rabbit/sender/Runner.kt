package com.github.rcd27.springtestcase.rabbit.sender

import com.github.rcd27.springtestcase.SpringTestCaseApplication.Companion.messageRoutingKey
import com.github.rcd27.springtestcase.SpringTestCaseApplication.Companion.topicExchangeName
import com.github.rcd27.springtestcase.rabbit.receiver.Receiver
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner(private val receiver: Receiver,
             private val template: RabbitTemplate
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Sending message")

        template.convertAndSend(
                topicExchangeName,
                "${messageRoutingKey}baz",
                "Whatsup backenders, nothing extra hard here, huh?"
        )
    }
}