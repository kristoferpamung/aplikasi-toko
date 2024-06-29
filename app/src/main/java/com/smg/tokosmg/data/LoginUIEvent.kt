package com.smg.tokosmg.data

import android.content.Context

sealed class LoginUIEvent {
    data class EmailChanged (val email: String) : LoginUIEvent()
    data class PasswordChanged (val password: String) : LoginUIEvent()
    data class LoginButtonClicked (val context: Context): LoginUIEvent()
}