package com.example.alien.excent.network

import com.example.alien.excent.ModelsApiClient.RegisterRequest
import com.example.alien.excent.ModelsApiClient.RegisterResponse
import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkApi {

    @POST("login/signin")
    fun submitSignIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

    @POST("login/signup")
    fun submitSignUp(@Body signUpRequest: RegisterRequest): Single<RegisterResponse>
}