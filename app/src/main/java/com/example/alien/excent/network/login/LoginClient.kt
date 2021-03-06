package com.example.alien.excent.network.login

import com.example.alien.excent.ModelsApiClient.RegisterRequest
import com.example.alien.excent.ModelsApiClient.RegisterResponse
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.ResponseConversions
import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginClient @Inject
internal constructor(private val api: NetworkApi){

    fun submitSignIn(user: String, password: String): Single<SignInResponse> {
        return api.submitSignIn(SignInRequest(user, password))
    }

    fun submitSignUp(user: String, email: String, password: String): Single<RegisterResponse> {
        return api.submitSignUp(RegisterRequest(user, password, email))
    }

    fun forgotPassword(email : String): Single<NetworkResult> {
        return api.forgotPassword(email)
            .toSingleDefault(NetworkResult.SUCCESS)
            .onErrorReturn{ ResponseConversions().toNetworkResult(it) }
    }
}