package com.example.alien.excent.network

import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST



interface NetworkApi {

    @POST("sessions/login")
    fun submitSignIn(@Body signInRequest: SignInRequest): Single<SignInResponse>
}