package com.smg.tokosmg.data.register

import android.content.Context

sealed class RegisterUIEvent {
    data class FullNameChanged (val fullName: String) : RegisterUIEvent()
    data class EmailChanged (val email: String) : RegisterUIEvent()
    data class PasswordChanged (val password: String) : RegisterUIEvent()
    data class ConfirmPasswordChanged (val confirmPassword: String) : RegisterUIEvent()

    data class RegisterButtonClicked (val context: Context) : RegisterUIEvent()
}