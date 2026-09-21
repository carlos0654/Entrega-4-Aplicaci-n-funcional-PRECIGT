package com.estefanie.preciogt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

fun validateName(value: String): String? = when {
    value.isBlank() -> "El nombre es obligatorio"
    value.trim().length < 3 -> "Escribe al menos 3 caracteres"
    else -> null
}

fun validateEmail(value: String): String? = when {
    value.isBlank() -> "El correo es obligatorio"
    !value.contains('@') || !value.substringAfter('@').contains('.') -> "Escribe un correo válido"
    else -> null
}

fun validateBudget(value: String): String? {
    val amount = value.toDoubleOrNull()
    return when {
        value.isBlank() -> "El presupuesto es obligatorio"
        amount == null -> "Ingresa solamente números"
        amount <= 0 -> "El presupuesto debe ser mayor que cero"
        else -> null
    }
}

fun isProfileFormValid(name: String, email: String, budget: String): Boolean =
    validateName(name) == null && validateEmail(email) == null && validateBudget(budget) == null

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var budget by rememberSaveable { mutableStateOf("") }
    var saved by rememberSaveable { mutableStateOf(false) }

    val nameError = validateName(name)
    val emailError = validateEmail(email)
    val budgetError = validateBudget(budget)
    val formIsValid = isProfileFormValid(name, email, budget)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Crear perfil de ahorro",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Completa los tres campos. El botón se habilita cuando todos sean válidos.",
            color = MaterialTheme.colorScheme.secondary
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                saved = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nombre completo") },
            singleLine = true,
            isError = nameError != null,
            supportingText = { Text(nameError.orEmpty()) }
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                saved = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = emailError != null,
            supportingText = { Text(emailError.orEmpty()) }
        )

        OutlinedTextField(
            value = budget,
            onValueChange = {
                budget = it
                saved = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Presupuesto mensual (Q)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = budgetError != null,
            supportingText = { Text(budgetError.orEmpty()) }
        )

        Button(
            onClick = { saved = true },
            enabled = formIsValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar perfil")
        }

        if (saved) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Perfil guardado localmente con datos mock.")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    PrecioGtTheme(dynamicColor = false) {
        AccountScreen()
    }
}
