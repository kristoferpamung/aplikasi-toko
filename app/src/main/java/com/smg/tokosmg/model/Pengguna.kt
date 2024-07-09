package com.smg.tokosmg.model

import com.google.firebase.Timestamp

data class Pengguna (
    val nama: String = "",
    val email: String = "",
    val fotoProfil: String = "",
    val nomorHp: String = "",
    val keranjang: List<ProdukItem> = emptyList(),
    val tanggalDaftar: Timestamp = Timestamp.now()
)



