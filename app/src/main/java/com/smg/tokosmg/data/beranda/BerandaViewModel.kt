package com.smg.tokosmg.data.beranda

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smg.tokosmg.model.Produk
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.repository.StorageRepository
import kotlinx.coroutines.launch

class BerandaViewModel(
    private val storageRepository: StorageRepository = StorageRepository(),
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {


    var berandaUiState by mutableStateOf(BerandaUiState())

    fun getAllProducts() = viewModelScope.launch {
        storageRepository.getProducts().collect {
            berandaUiState = berandaUiState.copy(
                productList = it
            )
        }
    }

    fun updateKeranjang (context: Context, produkItem: ProdukItem) = storageRepository.updateKeranjang(
        userId = authRepository.getUserId(),
        produk = produkItem,
        isCompleted = { isOk ->
            if (isOk) {
                Toast.makeText(context, "Berhasil Menambahkan Barang ke Keranjang", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Gagal Menambahkan Barang ke Keranjang", Toast.LENGTH_LONG).show()
            }
        }
    )
}

data class BerandaUiState (
    val productList: Resources<List<Produk>> = Resources.Loading()
)