package com.smg.tokosmg.model

data class Produk (
    val nama: String = "",
    val stok: Long = 0,
    val satuan: String = "",
    val gambar: String ="",
    val hargaProduk: List<HargaProduk> = emptyList()
)