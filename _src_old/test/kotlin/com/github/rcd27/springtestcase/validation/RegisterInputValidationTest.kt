package com.github.rcd27.springtestcase.validation

import com.github.rcd27.springtestcase.rest.RegisterUserRequest
import com.github.rcd27.springtestcase.validation.RegisterInputValidation.validate
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class RegisterInputValidationTest {

    private val validInput = RegisterUserRequest(
            firstName = "Stanislav",
            lastName = "Zemlyakov",
            email = "redtom@yandex.ru",
            dateOfBirth = "01.08.1989",
            habitatCity = "Innopolis",
            registrationCity = "Saint-Peteresburg"
    )

    @Test
    fun `all valid`() {
        val result = validate(validInput).block()

        Assert.isTrue(result?.isValid == true, "all should be fine")
    }

    @Test
    fun `invalid email`() {
        val input = validInput.copy(email = "dropdown.com")

        val result = validate(input).block()

        Assert.isTrue(result?.isInvalid == true, "the given email is invalid")
    }

    // TODO: cover all cases
}
