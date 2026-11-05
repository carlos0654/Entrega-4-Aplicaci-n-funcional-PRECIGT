package com.estefanie.preciogt

import com.estefanie.preciogt.ui.viewmodel.ProfileUiState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileValidationTest {

    @Test
    fun validProfileIsValid() {
        val state = ProfileUiState(
            name = "Carlos",
            email = "carlos@email.com",
            budget = "1500"
        )

        assertTrue(state.isFormValid)
    }

    @Test
    fun invalidProfileIsNotValid() {
        val state = ProfileUiState(
            name = "Ca",
            email = "correo-invalido",
            budget = "0"
        )

        assertFalse(state.isFormValid)
    }

    @Test
    fun errorsClearWhenValuesAreCorrected() {
        val invalidState = ProfileUiState(
            name = "A",
            email = "carlos",
            budget = "texto"
        )

        assertNotNull(invalidState.nameError)
        assertNotNull(invalidState.emailError)
        assertNotNull(invalidState.budgetError)

        val correctedState = invalidState.copy(
            name = "Carlos",
            email = "carlos@email.com",
            budget = "950.50"
        )

        assertNull(correctedState.nameError)
        assertNull(correctedState.emailError)
        assertNull(correctedState.budgetError)
        assertTrue(correctedState.isFormValid)
    }
}