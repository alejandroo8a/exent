package com.example.alien.excent.data.login.signin

import com.example.alien.excent.data.login.LoginType

data class SignInData(
    val token: String,
    val idUser: Int,
    val type: LoginType
)

