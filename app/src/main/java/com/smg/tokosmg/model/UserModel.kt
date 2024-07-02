package com.smg.tokosmg.model

import com.google.firebase.Timestamp

data class UserModel (
    val fullName: String = "",
    val email: String = "",
    val registerDate: Timestamp = Timestamp.now(),
    val profileImg: String = "",
    val isAdmin: Boolean = false
)