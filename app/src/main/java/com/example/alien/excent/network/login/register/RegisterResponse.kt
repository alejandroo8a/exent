package com.example.alien.excent.ModelsApiClient


data class RegisterResponse(
        val token: String,
        val idUser: Int,
        val userName: String,
        val email: String
)