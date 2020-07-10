package com.github.rcd27.mailer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MailerApplication

fun main() {
    runApplication<MailerApplication>()
}