package com.estefanie.preciogt.ui.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    private val profiles =
        db.collection("perfiles")

    private fun ensureSession(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser

        // Reutilizar la sesión existente.
        if (currentUser != null) {
            onSuccess(currentUser.uid)
            return
        }

        // Crear una sesión antes de consultar Firestore.
        auth.signInAnonymously()
            .addOnSuccessListener { result ->
                val uid = result.user?.uid

                if (uid != null) {
                    onSuccess(uid)
                } else {
                    onError("No se pudo obtener el usuario.")
                }
            }
            .addOnFailureListener { error ->
                onError(
                    error.localizedMessage
                        ?: "No se pudo iniciar la sesión."
                )
            }
    }
    fun saveProfile(
        profile: UserProfile,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        ensureSession(
            onSuccess = { uid ->
                val data = hashMapOf(
                    "nombre" to profile.name,
                    "email" to profile.email,
                    "presupuesto" to profile.budget
                )

                profiles.document(uid)
                    .set(data)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { error ->
                        onError(error.message ?: "Error al guardar")
                    }

            },
            onError = onError

        )



    }

    fun getProfile(
        onSuccess: (UserProfile?) -> Unit,
        onError: (String) -> Unit
    ) {
        ensureSession(
            onSuccess = {uid ->
                profiles.document(uid)
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
                        },
                onError = onError
        )

    }

}