package com.github.rcd27.api

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

// TODO: figure out if this is needed (@RestController == auto WebFlux?)
@Configuration
@EnableWebFlux
open class ApiConfiguration