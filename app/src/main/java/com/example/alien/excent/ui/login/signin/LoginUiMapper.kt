package com.example.alien.excent.ui.login.signin

import com.example.alien.excent.data.login.signin.SignInData
import com.example.alien.excent.data.login.signin.SignInType
import com.example.alien.excent.network.login.signin.UiSignInResult
import javax.inject.Inject


class LoginUiMapper @Inject
internal constructor() {

    fun toUiSignInResult(signInData: SignInData): UiSignInResult {
        return when (signInData.type) {
            SignInType.SUCCESS -> UiSignInResult.SUCCESS
            SignInType.INVALID_CREDENTIALS -> UiSignInResult.INVALID_CREDENTIALS
            SignInType.FORBIDDEN_ERROR -> UiSignInResult.FORBIDDEN_ERROR
            SignInType.CONNECTION_ERROR -> UiSignInResult.CONNECTION_ERROR
            SignInType.GENERIC_ERROR -> UiSignInResult.GENERIC_ERROR
        }
    }
}