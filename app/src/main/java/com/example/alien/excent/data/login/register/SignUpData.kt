package com.example.alien.excent.data.login.register

import com.example.alien.excent.data.login.LoginType

data class SignUpData (
    val token: String,
    val idUser: Int,
    val userName: String,
    val email: String,
    val type: LoginType
)