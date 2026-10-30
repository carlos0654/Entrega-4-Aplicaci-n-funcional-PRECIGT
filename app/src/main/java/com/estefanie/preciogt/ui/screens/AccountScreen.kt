package com.estefanie.preciogt.ui.screens

import android.content.Context
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.data.FirestoreRepository
import com.estefanie.preciogt.ui.data.UserProfile
import com.estefanie.preciogt.ui.theme.PrecioGtTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.estefanie.preciogt.ui.viewmodel.ProfileViewModel

@Composable
fun AccountScreen(
    viewModel: ProfileViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountContent(
        uiState = uiState,

        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onBudgetChange = viewModel::onBudgetChange,
        onSaveClick = viewModel::saveProfile
    )
}




/*
fun validateName(value: String): String? = when {
    value.isBlank() -> "El nombre es obligatorio"
    value.trim().length < 3 -> "Escribe al menos 3 caracteres"
    else -> null
}

fun validateEmail(value: String): String? = when {
    value.isBlank() -> "El correo es obligatorio"
    !value.contains('@') ||
            !value.substringAfter('@').contains('.') ->
        "Escribe un correo válido"
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

fun isProfileFormValid(
    name: String,
    email: String,
    budget: String
): Boolean =
    validateName(name) == null &&
            validateEmail(email) == null &&
            validateBudget(budget) == null
@Composable
fun AccountScreen(modifier: Modifier = Modifier) {

    val repository = remember {
        FirestoreRepository()
    }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    var saved by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var saving by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    // CARGAR PERFIL AL ABRIR LA PANTALLA
    LaunchedEffect(Unit) {

        repository.getProfile(
            onSuccess = { profile ->

                if (profile != null) {
                    name = profile.name
                    email = profile.email
                    budget = profile.budget.toString()
                }

                loading = false
            },

            onError = { mensaje ->

                error = mensaje
                loading = false
            }
        )
    }

    val nameError = validateName(name)
    val emailError = validateEmail(email)
    val budgetError = validateBudget(budget)

    val formIsValid =
        isProfileFormValid(name, email, budget)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Perfil de usuario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Completa todos los campos para configurar tu perfil.",
            color = MaterialTheme.colorScheme.secondary
        )

        if (loading) {

            CircularProgressIndicator()

            Text("Cargando perfil...")

        } else {

            // NOMBRE
            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                    saved = false
                    message = ""
                },

                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre completo") },
                singleLine = true,
                isError = nameError != null,
                supportingText = {
                    Text(nameError.orEmpty())
                }
            )

            OutlinedTextField(
                value = email,

                onValueChange = {
                    email = it
                    saved = false
                    message = ""
                },

                modifier = Modifier.fillMaxWidth(),
                label = { Text("Correo electrónico") },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),

                singleLine = true,
                isError = emailError != null,
                supportingText = {
                    Text(emailError.orEmpty())
                }
            )

            // PRESUPUESTO
            OutlinedTextField(
                value = budget,

                onValueChange = {
                    budget = it
                    saved = false
                    message = ""
                },

                modifier = Modifier.fillMaxWidth(),
                label = { Text("Presupuesto mensual (Q)") },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),

                singleLine = true,
                isError = budgetError != null,
                supportingText = {
                    Text(budgetError.orEmpty())
                }
            )

            // BOTÓN GUARDAR
            Button(
                onClick = {

                    val cantidad = budget.toDoubleOrNull()

                    if (cantidad != null) {

                        saving = true
                        saved = false
                        error = ""
                        message = ""

                        val profile = UserProfile(
                            name = name.trim(),
                            email = email.trim(),
                            budget = cantidad
                        )

                        repository.saveProfile(
                            profile = profile,

                            onSuccess = {

                                saving = false
                                saved = true
                                message = "Perfil guardado correctamente en Firestore."
                            },

                            onError = { mensaje ->

                                saving = false
                                error = mensaje
                            }
                        )
                    }
                },

                enabled = formIsValid &&
                        !saving &&
                        !loading,

                modifier = Modifier.fillMaxWidth()
            ) {

                if (saving) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                    Text("Guardando...")
                } else {
                    Text("Guardar perfil")
                }
            }

            // CONFIRMACIÓN
            if (saved) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(message)
                }
            }

            // ERROR
            if (error.isNotBlank()) {

                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}*/