package com.smg.tokosmg.util

data class ValidationResult (
    val successful : Boolean,
    val errorMessage: String? = null
)