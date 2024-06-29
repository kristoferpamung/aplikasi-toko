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

class LoginViewModel (
    private val authRepository: AuthRepository = AuthRepository(),
    private val formValidator: FormValidator = FormValidator()
) : ViewModel() {
    var loginUIState by mutableStateOf(LoginUIState())

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState = loginUIState.copy(
                    email = event.email
                )
            }
            is LoginUIEvent.PasswordChanged -> {
                loginUIState = loginUIState.copy(
                    password = event.password
                )
            }
            is LoginUIEvent.LoginButtonClicked -> {
                loginUser(event.context)
            }
        }
    }

    private fun loginUser(context: Context) = viewModelScope.launch {
        try {
            val emailResult = formValidator.isEmailValid(loginUIState.email)
            val passwordResult = formValidator.isPasswordValid(loginUIState.password)

            val hasError = listOf(
                emailResult,
                passwordResult
            ).any {!it.successful}

            if (hasError) {
                loginUIState = loginUIState.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            } else {
                loginUIState = loginUIState.copy(
                    isLoading = true,
                    emailError = null,
                    passwordError = null
                )
                authRepository.loginUser(email = loginUIState.email, password = loginUIState.password){ isSuccessful ->
                    if(isSuccessful) {
                        Toast.makeText(context, "Login Berhasil", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Login Gagal", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (e: Exception) {
            loginUIState = if (e.localizedMessage?.contains("A network error") == true) {
                loginUIState.copy(
                    errorFromFirebase = "Tidak ada koneksi Internet"
                )
            } else if (e.localizedMessage?.contains("auth credential is incorrect") == true) {
                loginUIState.copy(
                    errorFromFirebase = "Email atau password yang dimasukan salah"
                )
            } else {
                loginUIState.copy(
                    errorFromFirebase = "Terjadi kesalahan yang tidak diketahui"
                )
            }
            e.stackTrace
        } finally {
            loginUIState = loginUIState.copy(
                isLoading = false,
                errorFromFirebase = null
            )
        }
    }
}
