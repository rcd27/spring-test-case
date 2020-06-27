package com.github.rcd27.api.verification.data

import com.github.rcd27.api.entities.persist.VerificationProcess
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationProcessRepository : ReactiveMongoRepository<VerificationProcess, String>