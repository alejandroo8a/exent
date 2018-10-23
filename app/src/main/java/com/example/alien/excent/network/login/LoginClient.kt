package com.example.alien.excent.network.login

import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginClient @Inject
internal constructor(private val api: NetworkApi){

    fun submitSignIn(user: String, password: String): Single<SignInResponse> {
        return api.submitSignIn(SignInRequest(user, password))
    }
}