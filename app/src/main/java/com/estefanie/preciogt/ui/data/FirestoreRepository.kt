package com.estefanie.preciogt.ui.data

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    private val profiles =
        db.collection("perfiles")

    fun saveProfile(
        profile: UserProfile,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val data = hashMapOf(
            "nombre" to profile.name,
            "email" to profile.email,
            "presupuesto" to profile.budget
        )

        profiles.document("perfil_actual")
            .set(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { error ->
                onError(error.message ?: "Error al guardar")
            }
    }

    fun getProfile(
        onSuccess: (UserProfile?) -> Unit,
        onError: (String) -> Unit
    ) {

        profiles.document("perfil_actual")
            .get()
            .addOnSuccessListener { documento ->

                if (documento.exists()) {

                    val profile = UserProfile(
                        name = documento.getString("nombre") ?: "",
                        email = documento.getString("email") ?: "",
                        budget = documento.getDouble("presupuesto") ?: 0.0
                    )

                    onSuccess(profile)

                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { error ->
                onError(error.message ?: "Error al recuperar perfil")
            }
    }
}