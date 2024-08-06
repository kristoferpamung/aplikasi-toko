package com.smg.tokosmg.model

data class ProdukItem (
    val namaBarang: String = "",
    val bobot: Double = 1.0,
    val satuan: String = "",
    var jumlah: Long = 0,
    val harga: Long = 0,
    val subTotal: Long = 0
)