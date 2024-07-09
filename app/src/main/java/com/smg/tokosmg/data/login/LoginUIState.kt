package com.smg.tokosmg.data.login

data class LoginUIState (
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,

    var isLoading : Boolean = false,
    var errorFromFirebase: String? = null
)