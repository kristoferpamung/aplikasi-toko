package com.smg.tokosmg.repository

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.smg.tokosmg.model.Pengguna
import com.smg.tokosmg.model.Produk
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.model.Transaksi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val USER_COLLECTION_REF = "users"
const val PRODUCT_COLLECTION_REF = "products"
const val TRANSAKSI_COLLECTION_REF = "transaksi"

class StorageRepository {

    private val userRef : CollectionReference = Firebase.firestore.collection(USER_COLLECTION_REF)
    private val productRef : CollectionReference = Firebase.firestore.collection(PRODUCT_COLLECTION_REF)
    private val transaksiRef : CollectionReference = Firebase.firestore.collection(TRANSAKSI_COLLECTION_REF)

    fun tambahPengguna (
        userId: String,
        nama: String,
        email: String,
        tanggalDaftar: Timestamp,
        onCompleted: (Boolean) -> Unit
    ) {
        val pengguna = Pengguna (
            nama = nama,
            email = email,
            tanggalDaftar = tanggalDaftar,
            fotoProfil = "https://cdn.pixabay.com/photo/2018/11/13/21/43/avatar-3814049_1280.png"
        )
        userRef.document(userId)
            .set(pengguna)
            .addOnCompleteListener {
                onCompleted.invoke(it.isSuccessful)
            }
            .addOnFailureListener {
                onCompleted.invoke(false)
            }
    }

    fun getUser (userId: String)  : Flow<Resources<Pengguna>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = userRef.document(userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null && snapshot.exists()) {
                        val user = snapshot.toObject(Pengguna::class.java)
                        Resources.Success(data = user)
                    } else {
                        Resources.Error( throwable = e?.cause)
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

    fun getProducts () : Flow<Resources<List<Produk>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = productRef
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val products = snapshot.toObjects(Produk::class.java)
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
    fun updateKeranjang (produk: ProdukItem, userId: String, isCompleted: (Boolean) -> Unit) {
        userRef.document(userId)
            .get()
            .addOnSuccessListener {
                if (it !=null && it.exists()) {
                    val pengguna = it.toObject(Pengguna::class.java)
                    if (pengguna != null) {
                        val daftarItem = pengguna.keranjang.toMutableList()

                        val existingItem = daftarItem.find { item ->
                            item.namaBarang == produk.namaBarang && item.satuan == produk.satuan
                        }

                        if (existingItem != null) {
                            val updatedJumlah = existingItem.jumlah + produk.jumlah
                            val totalSubTotal = produk.harga * updatedJumlah

                            val updatedItem = existingItem.copy(
                                jumlah = updatedJumlah,
                                subTotal = totalSubTotal
                            )
                            daftarItem.remove(existingItem)
                            daftarItem.add(updatedItem)

                        } else {
                            daftarItem.add(produk)
                        }

                        val updatedKeranjang = pengguna.copy(keranjang = daftarItem)
                        userRef.document(userId)
                            .set(updatedKeranjang)
                            .addOnSuccessListener {
                                isCompleted.invoke(true)
                            }
                            .addOnFailureListener {
                                isCompleted.invoke(false)
                            }
                    }
                } else {
                    isCompleted.invoke(false)
                }
            }
            .addOnFailureListener { e ->
                println("Error mengambil dokumen pengguna: $e")
            }
    }

    fun getTansaksi (userId: String) : Flow<Resources<List<Transaksi>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = transaksiRef
                .whereEqualTo("idPengguna", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val transaksi = snapshot.toObjects(Transaksi::class.java)
                        Resources.Success(data = transaksi)
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