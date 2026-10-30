package com.estefanie.preciogt.ui.viewmodel


data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val budget: String = "",

    val loading: Boolean = true,
    val saving: Boolean = false,
    val saved: Boolean = false,

    val message: String = "",
    val error: String = ""
) {


    val nameError: String?
        get() = when {
            name.isBlank() ->
                "El nombre es obligatorio"

            name.trim().length < 3 ->
                "Escribe al menos 3 caracteres"

            else -> null
        }

    val emailError: String?
        get() = when {
            email.isBlank() ->
                "El correo es obligatorio"

            !email.contains('@') ||
                    !email.substringAfter('@').contains('.') ->
                "Escribe un correo válido"

            else -> null
        }

    val budgetError: String?
        get() {
            val amount = budget.toDoubleOrNull()

            return when {
                budget.isBlank() ->
                    "El presupuesto es obligatorio"

                amount == null ->
                    "Ingresa solamente números"

                amount <= 0 ->
                    "El presupuesto debe ser mayor que cero"

                else -> null
            }
        }


    val isFormValid: Boolean
        get() = nameError == null && emailError == null && budgetError == null
}
