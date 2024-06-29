package com.smg.tokosmg.util

import android.util.Patterns

class FormValidator {

    fun isFullNameValid (fullName: String) : ValidationResult {
        return if (fullName.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Nama lengkap tidak boleh kosong!"
            )
        } else {
            ValidationResult(
                successful = true
            )
        }
    }

    fun isEmailValid (email : String) : ValidationResult {
        return if (email.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Email tidak boleh kosong!"
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult(
                successful = false,
                errorMessage = "Format email tidak valid!"
            )
        } else {
            ValidationResult(
                successful = true
            )
        }
    }

    fun isPasswordValid (password: String) : ValidationResult {
        val passwordContainDigitAndLetter = password.any {it.isDigit()} && password.any {it.isLetter()}
        return if (password.length < 8) {
            ValidationResult(
                successful = false,
                errorMessage = "Kata sandi tidak boleh kurang dari 8 karakter!"
            )
        } else if (!passwordContainDigitAndLetter) {
            ValidationResult(
                successful = false,
                errorMessage = "Kata sandi harus memiliki minimal 1 huruf dan 1 angka!"
            )
        } else if (password.any { it.isWhitespace() }) {
            ValidationResult(
                successful = false,
                errorMessage = "Kata sandi tidak boleh menggunakan spasi!"
            )
        } else {
             ValidationResult(
                successful = true
            )
        }
    }

    fun isConfirmPasswordValid (password: String, confirmPassword: String) : ValidationResult {
        return if (confirmPassword.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Konfirmasi Kata Sandi Tidak Boleh Kosong!"
            )
        } else if (password != confirmPassword) {
            ValidationResult(
                successful = false,
                errorMessage = "Konfirmasi Kata Sandi Tidak Cocok Dengan Kata Sandi!"
            )
        } else {
            ValidationResult(
                successful = true
            )
        }
    }
}