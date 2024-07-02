package com.smg.tokosmg.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.smg.tokosmg.model.ProductPrice

@Composable
fun KeranjangScreen () {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val firestore = Firebase.firestore.collection("coba")

        val data = hashMapOf(
            "productName" to "Minyak Goreng",
            "productStock" to 10,
            "productPrice" to listOf(
                hashMapOf<String, Any>(
                    "unit" to "kg",
                    "price" to 20000
                ),
                hashMapOf<String, Any>(
                    "unit" to "dg",
                    "price" to 2500
                )
            )
        )

        Button(
            onClick = {
                firestore.add(data)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Produk berhasil ditambahkan dengan ID: ${it.id}")
                    }
                    .addOnFailureListener {
                        Log.w("Firestore", "Error menambahkan produk", it)
                    }
            }
        ) {
           Text(text = "post")
        }


        Button(
            onClick = {
                firestore.get()
                    .addOnSuccessListener {
                        for (product in it) {
                            val response = product.toObject(Response::class.java)
                            Log.d("Product", "KeranjangScreen: ${response.productName} + ${response.productStock} + ${response.productPrice[1]}")
                        }

                    }
                    .addOnFailureListener {
                        Log.w("Firestore", "Error menambahkan produk", it)
                    }
            }
        ) {
            Text(text = "get")
        }
    }
}

data class Response (
    val productName: String ="",
    val productStock: Long = 0,
    val productPrice: List<ProductPrice> = emptyList()
)