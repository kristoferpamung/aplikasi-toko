package com.smg.tokosmg.data

data class RegisterUIState (
    var fullName: String = "",
    var fullNameError: String? = null,
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var confirmPassword: String = "",
    var confirmPasswordError: String? = null,

    var isLoading : Boolean = false,
    var errorFromFirebase: String? = null
)

