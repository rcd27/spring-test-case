package com.github.rcd27.springtestcase.db

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    private val id: String? = null,

    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val habitatCity: String?,
    val registrationCity: String?
)
