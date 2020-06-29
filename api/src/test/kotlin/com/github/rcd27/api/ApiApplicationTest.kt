package com.github.rcd27.api

import org.hamcrest.CoreMatchers.any
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert

@SpringBootTest
class ApiApplicationTest {

  @Test
  fun main() {
    val matches = any(String::class.java).matches("")
    Assert.isTrue(matches, "wow")
  }
}