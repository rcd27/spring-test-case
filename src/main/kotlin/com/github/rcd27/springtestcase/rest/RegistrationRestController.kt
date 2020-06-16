package com.github.rcd27.springtestcase.rest

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/register")
class RegistrationRestController {

    @PostMapping("/user")
    fun registerByName(@RequestBody registerUserRequest: RegisterUserRequest): Mono<String> {

        return Mono.fromCallable {
            println("Got a registration input: $registerUserRequest")
            "OK MAN"
        }
    }
}

