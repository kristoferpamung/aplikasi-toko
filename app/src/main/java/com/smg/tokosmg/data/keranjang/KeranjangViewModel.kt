package com.smg.tokosmg.data.keranjang

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.smg.tokosmg.model.Pengguna
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.repository.AuthRepository
import kotlinx.coroutines.launch

class KeranjangViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val penggunaRef = Firebase.firestore.collection("users").document(authRepository.getUserId())
    val transaksiRef = Firebase.firestore.collection("transaksi").document()

    fun updateJumlahProduk (produkItem : ProdukItem, jumlahBaru : Int) {
        viewModelScope.launch {
            penggunaRef.get().addOnSuccessListener { userDocument ->
                if (userDocument.exists() && userDocument != null) {
                    val pengguna = userDocument.toObject(Pengguna::class.java)
                    if (pengguna != null) {
                        val updateKeranjang = pengguna.keranjang.map {
                            if (it.namaBarang == produkItem.namaBarang && it.satuan == produkItem.satuan) {
                                it.copy(
                                    jumlah = jumlahBaru.toLong(),
                                    subTotal = jumlahBaru * it.harga
                                )
                            } else {
                                it
                            }
                        }

                        penggunaRef.update("keranjang", updateKeranjang)
                            .addOnCompleteListener {
                                Log.d("UpdateBarang", "Terupdate")
                            }
                            .addOnFailureListener {
                                Log.d("UpdateBarang", "Tidak terupdate")
                            }
                    }
                }
            }
        }
    }

    fun hapusBarangKeranjang (produkItem: ProdukItem) {
        viewModelScope.launch {
            penggunaRef.get().addOnSuccessListener { userDocument ->
                if (userDocument != null && userDocument.exists()) {
                    val pengguna = userDocument.toObject(Pengguna::class.java)
                    if (pengguna != null) {
                        val updatedKeranjang = pengguna.keranjang.filterNot {
                            it.namaBarang == produkItem.namaBarang && it.satuan == produkItem.satuan
                        }
                        penggunaRef.update("keranjang", updatedKeranjang)
                            .addOnSuccessListener {
                                Log.d("hapusBarang", "hapusBarangKernjang: berhasil hapus item")
                            }
                            .addOnFailureListener { e ->
                                Log.d("hapusBarang", "hapusBarangKernjang: gagal hapus item")
                            }
                    }
                }
            }
        }
    }

    fun hapusSemuaItem() {
        viewModelScope.launch {
            penggunaRef.update("keranjang", emptyList<ProdukItem>())
        }
    }

    fun simpanTransaksi(transaksi: Transaksi, context: Context) {
        viewModelScope.launch {
            val transaksiUserId = transaksi.copy(
                idPengguna = authRepository.getUserId()
            )
            transaksiRef.set(transaksiUserId)
                .addOnSuccessListener {
                    Toast.makeText( context,"Berhasil menambahkan pesanan", Toast.LENGTH_LONG).show()
                    hapusSemuaItem()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal menambahkan pesanan", Toast.LENGTH_LONG).show()
                }
        }
    }
}