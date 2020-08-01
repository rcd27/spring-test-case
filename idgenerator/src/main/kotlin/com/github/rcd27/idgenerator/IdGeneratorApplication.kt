package com.github.rcd27.idgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class IdGeneratorApplication

fun main() {
  runApplication<IdGeneratorApplication>()
}