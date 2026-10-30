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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.viewmodel.ProfileUiState
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

@Composable
fun AccountContent(
    uiState: ProfileUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBudgetChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {

    Column(
        modifier = Modifier
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

        if (uiState.loading) {

            CircularProgressIndicator()

            Text("Cargando perfil...")

        } else {





            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre completo") },
                singleLine = true,
                isError = uiState.nameError != null,
                supportingText = {
                    Text(uiState.nameError.orEmpty())
                }
            )





            OutlinedTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                isError = uiState.emailError != null,
                supportingText = {
                    Text(uiState.emailError.orEmpty())
                }
            )





            OutlinedTextField(
                value = uiState.budget,
                onValueChange = onBudgetChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Presupuesto mensual (Q)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true,
                isError = uiState.budgetError != null,
                supportingText = {
                    Text(uiState.budgetError.orEmpty())
                }
            )

            Button(
                onClick = onSaveClick,
                enabled = uiState.isFormValid &&
                        !uiState.saving &&
                        !uiState.loading,
                modifier = Modifier.fillMaxWidth()
            ) {

                if (uiState.saving) {

                    CircularProgressIndicator(
                        modifier = Modifier.padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )

                    Text("Guardando...")

                } else {

                    Text("Guardar perfil")
                }
            }



            if (uiState.saved) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(uiState.message)
                }
            }

            if (uiState.error.isNotBlank()) {

                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}










@Preview(showBackground = true)
@Composable
private fun AccountContentPreview() {

    PrecioGtTheme(dynamicColor = false) {

        AccountContent(
            uiState = ProfileUiState(
                name = "Carlos",
                email = "carlos@gmail.com",
                budget = "1500",
                loading = false
            ),
            onNameChange = {},
            onEmailChange = {},
            onBudgetChange = {},
            onSaveClick = {}
        )
    }
}
