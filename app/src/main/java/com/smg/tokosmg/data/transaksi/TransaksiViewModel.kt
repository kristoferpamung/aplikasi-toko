package com.smg.tokosmg.data.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.repository.StorageRepository
import kotlinx.coroutines.launch

class TransaksiViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val storageRepository: StorageRepository = StorageRepository()
) : ViewModel() {

    var transaksiUiState by mutableStateOf(TransaksiUiState())

    fun getAllTransaksi() {
        viewModelScope.launch {
            storageRepository.getTansaksi(authRepository.getUserId()).collect() {
                transaksiUiState = transaksiUiState.copy(
                    transaksiList = it
                )
            }
        }
    }
}

data class TransaksiUiState (
    val transaksiList: Resources<List<Transaksi>> = Resources.Loading()
)