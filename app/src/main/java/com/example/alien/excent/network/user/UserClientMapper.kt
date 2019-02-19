package com.example.alien.excent.network.user

import javax.inject.Inject

class UserClientMapper @Inject
internal constructor() {

    fun toChangePasswordRequest(oldPassword: String, newPassword: String): ChangePasswordRequest {
        return ChangePasswordRequest(oldPassword, newPassword)
    }
}