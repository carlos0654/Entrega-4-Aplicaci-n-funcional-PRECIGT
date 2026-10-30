package com.estefanie.preciogt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estefanie.preciogt.ui.data.FirestoreRepository

class ProfileViewModelFactory(
    private val repository: FirestoreRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido: ${modelClass.name}"
        )
    }
}
