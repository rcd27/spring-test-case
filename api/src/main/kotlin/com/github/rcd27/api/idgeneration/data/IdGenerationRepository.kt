package com.github.rcd27.api.idgeneration.data

import reactor.core.publisher.Mono

interface IdGenerationRepository {

  fun generateUniqueId(): Mono<String>
  }