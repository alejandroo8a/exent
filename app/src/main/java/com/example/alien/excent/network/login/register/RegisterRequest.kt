package com.example.alien.excent.ModelsApiClient

data class RegisterRequest(
        val user: String,
        val password: String,
        val email: String
)