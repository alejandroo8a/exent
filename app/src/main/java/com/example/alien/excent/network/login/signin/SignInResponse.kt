package com.example.alien.excent.network.login.signin

data class SignInResponse(
        val token: String,
        val idUser: Int,
        val userName: String,
        val email: String
)
