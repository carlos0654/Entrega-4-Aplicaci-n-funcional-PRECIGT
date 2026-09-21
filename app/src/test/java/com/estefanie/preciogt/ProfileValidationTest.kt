package com.estefanie.preciogt

import com.estefanie.preciogt.ui.screens.isProfileFormValid
import com.estefanie.preciogt.ui.screens.validateBudget
import com.estefanie.preciogt.ui.screens.validateEmail
import com.estefanie.preciogt.ui.screens.validateName
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileValidationTest {
    @Test
    fun validProfileEnablesSubmission() {
        assertTrue(isProfileFormValid("Carlos", "carlos@email.com", "1500"))
    }

    @Test
    fun invalidProfileKeepsSubmissionDisabled() {
        assertFalse(isProfileFormValid("Ca", "correo-invalido", "0"))
    }

    @Test
    fun validatorsReportAndClearErrorsInRealTime() {
        assertNotNull(validateName("A"))
        assertNotNull(validateEmail("carlos"))
        assertNotNull(validateBudget("texto"))
        assertNull(validateName("Carlos"))
        assertNull(validateEmail("carlos@email.com"))
        assertNull(validateBudget("950.50"))
    }
}
