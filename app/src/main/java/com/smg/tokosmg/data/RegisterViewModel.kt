package com.smg.tokosmg.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.util.FormValidator
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val authRepository: AuthRepository = AuthRepository(),
    private val formValidator: FormValidator = FormValidator()
) : ViewModel () {
    var registerUIState by mutableStateOf(RegisterUIState())
        private set

    fun onEvent(event: RegisterUIEvent){
        when (event) {
            is RegisterUIEvent.FullNameChanged -> {
                registerUIState = registerUIState.copy(
                    fullName = event.fullName
                )
            }
            is RegisterUIEvent.EmailChanged -> {
                registerUIState = registerUIState.copy(
                    email = event.email
                )
            }
            is RegisterUIEvent.PasswordChanged -> {
                registerUIState = registerUIState.copy(
                    password = event.password
                )
            }
            is RegisterUIEvent.ConfirmPasswordChanged -> {
                registerUIState = registerUIState.copy(
                    confirmPassword = event.confirmPassword
                )
            }
            is RegisterUIEvent.RegisterButtonClicked -> {
                registerUser(event.context)
            }
        }
    }
    private fun registerUser (context: Context)  = viewModelScope.launch {
        try {
            val fullNameResult = formValidator.isFullNameValid(registerUIState.fullName)
            val emailResult = formValidator.isEmailValid(registerUIState.email)
            val passwordResult = formValidator.isPasswordValid(registerUIState.password)
            val confirmPassword = formValidator.isConfirmPasswordValid(registerUIState.password, registerUIState.confirmPassword)

            val hasError = listOf(
                fullNameResult,
                emailResult,
                passwordResult,
                confirmPassword
            ).any {!it.successful}

            if (hasError) {
                registerUIState = registerUIState.copy(
                    fullNameError = fullNameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    confirmPasswordError = confirmPassword.errorMessage
                )
            } else {
                registerUIState = registerUIState.copy(
                    isLoading = true,
                    fullNameError = null,
                    emailError = null,
                    passwordError = null,
                    confirmPasswordError = null
                )
                authRepository.createUser(
                    email = registerUIState.email,
                    password = registerUIState.password
                ) { isSuccessful ->
                    if (isSuccessful) {
                        Toast.makeText(context, "Berhasil Mendaftarkan Akun", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Gagal Mendaftarkan Akun", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (e: Exception) {
            registerUIState = if (e.localizedMessage?.contains("A network error") == true) {
                registerUIState.copy(
                    errorFromFirebase = "Tidak ada koneksi Internet"
                )
            } else if (e.localizedMessage?.contains("The email address is already") == true) {
                registerUIState.copy(
                    errorFromFirebase = "Email sudah digunakan, mohon masukan email lain."
                )
            } else {
                registerUIState.copy(
                    errorFromFirebase = "Terjadi error yang tidak diketahui"
                )
            }
            e.printStackTrace()
        } finally {
            registerUIState = registerUIState.copy(
                isLoading = false,
                errorFromFirebase = null
            )
        }
    }

}