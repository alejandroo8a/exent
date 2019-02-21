package com.example.alien.excent.network

import com.example.alien.excent.ModelsApiClient.RegisterRequest
import com.example.alien.excent.ModelsApiClient.RegisterResponse
import com.example.alien.excent.network.core.EventsResponse
import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import com.example.alien.excent.network.user.ChangePasswordRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApi {

    @POST("settings/change_password")
    fun changePassword(@Body changePassword: ChangePasswordRequest): Completable

    @POST("login/forgot_password")
    fun forgotPassword(@Body email: String): Completable

    @GET("events/{idLocation}/{idCategory}")
    fun getEvents(@Path("idLocation") idLocation: Int,
                  @Path("idCategory") idCategory: Int) : Single<EventsResponse>

    @GET("events/search/{idLocation}/{event}")
    fun searchEvents(@Path("idLocation") idLocation: Int,
                     @Path("event") event: String) : Single<EventsResponse>

    @POST("login/signin")
    fun submitSignIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

    @POST("login/signup")
    fun submitSignUp(@Body signUpRequest: RegisterRequest): Single<RegisterResponse>
}