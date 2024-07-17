package com.smg.tokosmg.data.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.smg.tokosmg.model.Pengguna
import com.smg.tokosmg.model.Transaksi
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val storageRepository: StorageRepository = StorageRepository(),
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val db = FirebaseFirestore.getInstance().collection("transaksi")
    private val idUser = FirebaseAuth.getInstance().currentUser?.uid
    private var listenerRegistration : ListenerRegistration? = null

    val hasUser : Boolean
        get() = authRepository.hasUser()

    var currentUser by mutableStateOf(HomeUiState())

    private val _listTransaksi = MutableStateFlow<List<Transaksi>>(emptyList())
    val listTransaksi: StateFlow<List<Transaksi>> get() = _listTransaksi

    init {
        fetchTransaksiDiterima()
    }

    fun getUser () = viewModelScope.launch {
        storageRepository.getUser(authRepository.getUserId()).collect {
            currentUser = currentUser.copy(
                user = it
            )
        }
    }

    fun logout () = authRepository.logOut()

    fun fetchTransaksiDiterima() {

        listenerRegistration = db
            .whereEqualTo("idPengguna", idUser)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    val productList = value.toObjects(Transaksi::class.java)
                    _listTransaksi.value = productList
                }
        }
    }

}

data class HomeUiState (
    val user : Resources<Pengguna> = Resources.Loading()
)
