package com.github.rcd27.api.verification.data

import com.github.rcd27.api.entities.persist.VerificationProcess
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationProcessRepository : ReactiveCrudRepository<VerificationProcess, String>

// FIXME (optional): add a method for custom query, for example `getByStatus`