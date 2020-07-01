package com.github.rcd27.api

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

// This is not required, since Spring cat detect WebFlux controllers
@Configuration
@EnableWebFlux
open class ApiConfiguration