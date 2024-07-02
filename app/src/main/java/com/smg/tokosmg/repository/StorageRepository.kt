package com.smg.tokosmg.repository

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.smg.tokosmg.model.ProductModel
import com.smg.tokosmg.model.UserModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val USER_COLLECTION_REF = "users"
const val PRODUCT_COLLECTION_REF = "products"

class StorageRepository {

    private val userRef : CollectionReference = Firebase.firestore.collection(USER_COLLECTION_REF)
    private val productRef : CollectionReference = Firebase.firestore.collection(PRODUCT_COLLECTION_REF)

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

    fun getProducts () : Flow<Resources<List<ProductModel>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = productRef
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val products = snapshot.toObjects(ProductModel::class.java)
                        Resources.Success(data = products)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}