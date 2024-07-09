package com.smg.tokosmg.model

data class ProdukItem (
    val namaBarang: String = "",
    val satuan: String = "",
    var jumlah: Long = 0,
    val harga: Long = 0,
    val subTotal: Long = 0
)