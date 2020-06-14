package com.github.rcd27.springtestcase.receiver

import org.springframework.stereotype.Component

@Component
class Receiver {

    companion object {
        const val receiveMethodName = "receiveMessage"
    }

    fun receiveMessage(message: String) {
        println("Received <$message>")
    }
}