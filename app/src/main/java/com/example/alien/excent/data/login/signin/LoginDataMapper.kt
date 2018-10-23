package com.example.alien.excent.data.login.signin

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.ResponseConversions
import com.example.alien.excent.network.login.signin.SignInResponse
import timber.log.Timber
import javax.inject.Inject


class LoginDataMapper @Inject
internal constructor(private val responseConversions: ResponseConversions){

    fun toSignInData(signInResponse: SignInResponse): SignInData {
        return SignInData(signInResponse.token, signInResponse.idUser, SignInType.SUCCESS)
    }

    fun toSignInDataError(throwable: Throwable): SignInData {
        Timber.w("Error submitting login information: %s", throwable.message)
        val result = responseConversions.toNetworkResult(throwable)
        val type = when {
            result === NetworkResult.CONNECTION_ERROR -> SignInType.CONNECTION_ERROR
            result === NetworkResult.AUTHORIZATION_ERROR -> SignInType.INVALID_CREDENTIALS
            result === NetworkResult.FORBIDDEN_ERROR -> SignInType.FORBIDDEN_ERROR
            else -> SignInType.GENERIC_ERROR
        }
        return SignInData("", 0, type)
    }
}