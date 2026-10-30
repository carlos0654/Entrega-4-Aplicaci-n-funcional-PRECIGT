package com.estefanie.preciogt.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.estefanie.preciogt.ui.data.FirestoreRepository
import com.estefanie.preciogt.ui.data.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun onNameChange(value: String) {
        _uiState.update { current ->
            current.copy(
                name = value,
                saved = false,
                message = "",
                error = ""
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { current ->
            current.copy(
                email = value,
                saved = false,
                message = "",
                error = ""
            )
        }
    }

    fun onBudgetChange(value: String) {
        _uiState.update { current ->
            current.copy(
                budget = value,
                saved = false,
                message = "",
                error = ""
            )
        }
    }

    fun loadProfile() {

        _uiState.update {
            it.copy(
                loading = true,
                error = ""
            )
        }

        repository.getProfile(

            onSuccess = { profile ->

                _uiState.update { current ->

                    if (profile != null) {
                        current.copy(
                            name = profile.name,
                            email = profile.email,
                            budget = profile.budget.toString(),
                            loading = false
                        )
                    } else {
                        current.copy(
                            loading = false
                        )
                    }
                }
            },

            onError = { mensaje ->

                _uiState.update {
                    it.copy(
                        loading = false,
                        error = mensaje
                    )
                }
            }
        )
    }

    fun saveProfile() {

        val current = _uiState.value

        if (!current.isFormValid) return

        val amount = current.budget.toDoubleOrNull()
            ?: return

        val profile = UserProfile(
            name = current.name.trim(),
            email = current.email.trim(),
            budget = amount
        )

        _uiState.update {
            it.copy(
                saving = true,
                saved = false,
                message = "",
                error = ""
            )
        }

        repository.saveProfile(

            profile = profile,

            onSuccess = {

                _uiState.update {
                    it.copy(
                        saving = false,
                        saved = true,
                        message = "Perfil guardado correctamente en Firestore."
                    )
                }
            },

            onError = { mensaje ->

                _uiState.update {
                    it.copy(
                        saving = false,
                        error = mensaje
                    )
                }
            }
        )
    }
}
