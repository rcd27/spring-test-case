package com.github.rcd27.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
open class TestController {

  @GetMapping("/test")
  fun test() = "Fine, fine, you got it"
}