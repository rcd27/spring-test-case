package com.github.rcd27.springtestcase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTestCaseApplication

fun main(args: Array<String>) {
	runApplication<SpringTestCaseApplication>(*args)
}
