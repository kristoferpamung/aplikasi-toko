package com.smg.tokosmg.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    val currentUser : FirebaseUser? = Firebase.auth.currentUser

    fun hasUser() : Boolean = Firebase.auth.currentUser != null

    fun getUserId() : String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(
        email: String,
        password: String,
        onCompleted: (Boolean) -> Unit,
    ): AuthResult = withContext(Dispatchers.IO) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onCompleted.invoke(true)
                } else {
                    onCompleted.invoke(false)
                }
            }.await()
    }

    suspend fun loginUser(
        email: String,
        password: String,
        onCompleted: (Boolean) -> Unit
    ): AuthResult = withContext(Dispatchers.IO) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onCompleted.invoke(true)
                } else {
                    onCompleted.invoke(false)
                }
            }
            .await()
    }

    fun logOut() = Firebase.auth.signOut()
}