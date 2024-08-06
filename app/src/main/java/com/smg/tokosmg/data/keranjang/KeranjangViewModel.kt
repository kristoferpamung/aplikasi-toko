package com.smg.tokosmg.data.keranjang

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.smg.tokosmg.model.Pengguna
import com.smg.tokosmg.model.Produk
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class KeranjangViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val penggunaRef = Firebase.firestore.collection("users").document(authRepository.getUserId())
    val transaksiRef = Firebase.firestore.collection("transaksi")
    private val db = FirebaseFirestore.getInstance()
    private val produkRef = db.collection("products")

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
                                Log.d("hapusBarang", "hapusBarangKernjang: gagal hapus item $e")
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

    fun simpanTransaksi(transaksi: Transaksi, context: Context, transaksiOk: (Boolean) -> Unit) {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        viewModelScope.launch {
            transaksiRef
                .whereEqualTo("idPengguna", authRepository.getUserId())
                .whereGreaterThan("tanggal", Timestamp(startOfDay))
                .whereLessThanOrEqualTo("tanggal", Timestamp(endOfDay))
                .get()
                .addOnSuccessListener { it ->
                    val adaTransaksi = it.toObjects(Transaksi::class.java)

                    val belumSelesai = adaTransaksi.any { ada ->
                        ada.statusTransaksi != "Selesai"
                    }

                    if (belumSelesai) {
                        transaksiOk.invoke(false)
                    } else {
                        penggunaRef.get().addOnSuccessListener {
                            val pengguna = it.toObject(Pengguna::class.java)
                            val transaksiUserId = transaksi.copy(
                                idPengguna = authRepository.getUserId(),
                                namaPengguna = pengguna?.nama ?: ""
                            )
                            transaksiRef.add(transaksiUserId)
                                .addOnSuccessListener {
                                    Toast.makeText( context,"Berhasil menambahkan pesanan", Toast.LENGTH_LONG).show()
                                    hapusSemuaItem()
                                    transaksiOk.invoke(true)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Gagal menambahkan pesanan", Toast.LENGTH_LONG).show()
                                    transaksiOk.invoke(false)
                                }
                        }
                    }
                }

        }
    }

    fun kurangiStok (listItem : List<ProdukItem>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            for (item in listItem) {
                val querySnapshot = produkRef.whereEqualTo("nama", item.namaBarang).get().await()
                for (document in querySnapshot.documents){
                    val produk = document.toObject(Produk::class.java)

                    produk?.let {
                        val stokBaru = it.stok - (item.jumlah * item.bobot)
                        if (stokBaru >= 0) {
                            document.reference.update("stok", stokBaru).await()
                        } else {
                            println("Stok tidak mencukupi untuk ${item.namaBarang}")
                        }
                    }
                }
            }
            onSuccess.invoke()
        }
    }

    fun tambahStok (listItem : List<ProdukItem>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            for (item in listItem) {
                val querySnapshot = produkRef.whereEqualTo("nama", item.namaBarang).get().await()
                for (document in querySnapshot.documents){
                    val produk = document.toObject(Produk::class.java)

                    produk?.let {
                        val stokBaru = it.stok + (item.jumlah * item.bobot)
                        if (stokBaru >= 0) {
                            document.reference.update("stok", stokBaru).await()
                        } else {
                            println("Stok tidak mencukupi untuk ${item.namaBarang}")
                        }
                    }
                }
            }
            onSuccess.invoke()
        }
    }
}