package com.smg.tokosmg.data.home

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smg.tokosmg.model.Pengguna
import com.smg.tokosmg.model.ProdukItem
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.repository.Resources
import com.smg.tokosmg.repository.StorageRepository
import kotlinx.coroutines.launch

class HomeViewModel (
    private val storageRepository: StorageRepository = StorageRepository(),
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val hasUser : Boolean
        get() = authRepository.hasUser()

    var currentUser by mutableStateOf(HomeUiState())

    fun getUser () = viewModelScope.launch {
        storageRepository.getUser(authRepository.getUserId()).collect {
            currentUser = currentUser.copy(
                user = it
            )
        }
    }


    fun logout () = authRepository.logOut()

}

data class HomeUiState (
    val user : Resources<Pengguna> = Resources.Loading()
)
