package com.smg.tokosmg.repository

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.smg.tokosmg.model.UserModel


const val USER_COLLECTION_REF = "users"

class StorageRepository {

    private val userRef : CollectionReference = Firebase.firestore.collection(USER_COLLECTION_REF)

    // Menambahkan Akun
    fun addUser(
        userId: String,
        fullName: String,
        email: String,
        registerDate: Timestamp,
        onCompleted: (Boolean) -> Unit
    ) {
        val newUser = UserModel (
            email = email,
            fullName = fullName,
            registerDate = registerDate
        )
        userRef.document(userId)
            .set(newUser)
            .addOnCompleteListener {
                onCompleted.invoke(it.isSuccessful)
            }
    }

    // Mengambil data user
    fun getUser(
        userId: String,
        onError:(Throwable?) -> Unit,
        onSuccess: (UserModel?) -> Unit
    ) {
        userRef.document(userId).get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(UserModel::class.java))
            }
            .addOnFailureListener {
                onError.invoke(it.cause)
            }
    }
}