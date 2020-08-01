package com.github.rcd27.approver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ApproverApplication

fun main(args: Array<String>) {
  runApplication<ApproverApplication>(*args)
}