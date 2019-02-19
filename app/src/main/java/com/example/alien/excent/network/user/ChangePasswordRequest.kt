package com.example.alien.excent.network.user

data class ChangePasswordRequest (
    val oldPassword: String,
    val newPassword: String
)