package com.github.rcd27.springtestcase.rabbit.receiver

import org.springframework.stereotype.Component

@Component
class RabbitMQReceiver {

    companion object {
        const val receiveMethodName = "receiveMessage"
    }

    fun receiveMessage(message: String) {
        println("Received <$message>")
    }
}
