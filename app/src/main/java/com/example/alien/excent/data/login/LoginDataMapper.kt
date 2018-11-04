package com.example.alien.excent.data.login

import com.example.alien.excent.ModelsApiClient.RegisterResponse
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.login.register.SignUpData
import com.example.alien.excent.data.login.signin.SignInData
import com.example.alien.excent.network.ResponseConversions
import com.example.alien.excent.network.login.signin.SignInResponse
import timber.log.Timber
import javax.inject.Inject


class LoginDataMapper @Inject
internal constructor(private val responseConversions: ResponseConversions){

    fun toSignInData(signInResponse: SignInResponse): SignInData {
        return SignInData(
            signInResponse.token,
            signInResponse.idUser,
            signInResponse.userName,
            signInResponse.email,
            LoginType.SUCCESS
        )
    }

    fun toSignInDataError(throwable: Throwable): SignInData {
        Timber.w("Error submitting login information: %s", throwable.message)
        val result = responseConversions.toNetworkResult(throwable)
        val type = when {
            result === NetworkResult.CONNECTION_ERROR -> LoginType.CONNECTION_ERROR
            result === NetworkResult.AUTHORIZATION_ERROR -> LoginType.INVALID_CREDENTIALS
            result === NetworkResult.FORBIDDEN_ERROR -> LoginType.FORBIDDEN_ERROR
            else -> LoginType.GENERIC_ERROR
        }
        return SignInData("", 0, "", "", type)
    }

    fun toSignUpData(signUpResponse: RegisterResponse): SignUpData {
        return SignUpData(
            signUpResponse.token,
            signUpResponse.idUser,
            signUpResponse.userName,
            signUpResponse.email,
            LoginType.SUCCESS
        )
    }

    fun toSignUpDataError(throwable: Throwable): SignUpData {
        Timber.w("Error submitting login information: %s", throwable.message)
        val result = responseConversions.toNetworkResult(throwable)
        val type = when {
            result === NetworkResult.CONNECTION_ERROR -> LoginType.CONNECTION_ERROR
            result === NetworkResult.AUTHORIZATION_ERROR -> LoginType.INVALID_CREDENTIALS
            result === NetworkResult.FORBIDDEN_ERROR -> LoginType.FORBIDDEN_ERROR
            else -> LoginType.GENERIC_ERROR
        }
        return SignUpData("", 0, "", "", type)
    }
}