package com.example.alien.excent.ui.login

import com.example.alien.excent.data.login.signin.SignInData
import com.example.alien.excent.data.login.LoginType
import com.example.alien.excent.data.login.register.SignUpData
import com.example.alien.excent.network.login.UiLoginResult
import javax.inject.Inject

class LoginUiMapper @Inject
internal constructor() {

    fun toUiSignInResult(signInData: SignInData): UiLoginResult {
        return when (signInData.type) {
            LoginType.SUCCESS -> UiLoginResult.SUCCESS
            LoginType.INVALID_CREDENTIALS -> UiLoginResult.INVALID_CREDENTIALS
            LoginType.FORBIDDEN_ERROR -> UiLoginResult.FORBIDDEN_ERROR
            LoginType.CONNECTION_ERROR -> UiLoginResult.CONNECTION_ERROR
            LoginType.GENERIC_ERROR -> UiLoginResult.GENERIC_ERROR
        }
    }

    fun toUiSignInResult(signUpData: SignUpData): UiLoginResult {
        return when (signUpData.type) {
            LoginType.SUCCESS -> UiLoginResult.SUCCESS
            LoginType.INVALID_CREDENTIALS -> UiLoginResult.INVALID_CREDENTIALS
            LoginType.FORBIDDEN_ERROR -> UiLoginResult.FORBIDDEN_ERROR
            LoginType.CONNECTION_ERROR -> UiLoginResult.CONNECTION_ERROR
            LoginType.GENERIC_ERROR -> UiLoginResult.GENERIC_ERROR
        }
    }
}