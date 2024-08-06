package com.smg.tokosmg.model

import com.google.firebase.firestore.DocumentId

data class Produk (
    @DocumentId val id: String = "",
    val nama: String = "",
    val stok: Long = 0,
    val satuan: String = "",
    val hargaProduk: List<HargaProduk> = emptyList()
)