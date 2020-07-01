package com.github.rcd27.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
open class TestController {

  @GetMapping("/test")
  fun handle(): Mono<String> = Mono.just("Fine, fine, you got it")
}