package com.example.alien.excent.data.login

import com.example.alien.excent.data.login.register.SignUpData
import com.example.alien.excent.data.login.signin.SignInData
import com.example.alien.excent.network.login.LoginClient
import com.example.alien.excent.preferences.auth.AuthPreferences
import io.reactivex.Single
import javax.inject.Inject
import com.example.alien.excent.preferences.auth.MutableAuthPreferences

class LoginRepository @Inject
internal constructor(
    private val authPreferences: AuthPreferences, private val mutableAuthPreferences: MutableAuthPreferences,
    private val client: LoginClient, private val mapperData: LoginDataMapper
) {

    fun isSessionActive(): Boolean {
        return authPreferences.getUserId() != 0L
    }

    fun submitLoginInformation(email: String, password: String): Single<SignInData> {
        return client.submitSignIn(email, password)
            .map(mapperData::toSignInData)
            .doOnSuccess(this::saveUser)
            .onErrorReturn { error -> mapperData.toSignInDataError(error) }
    }

    fun submitNewUser(user: String, email: String, password: String): Single<SignUpData> {
        return client.submitSignUp(user, email, password)
            .map(mapperData::toSignUpData)
            .doOnSuccess(this::saveUser)
            .onErrorReturn { error -> mapperData.toSignUpDataError(error) }
    }

    private fun saveUser(signInData: SignInData) {
        mutableAuthPreferences.saveAuthToken(signInData.token)
        mutableAuthPreferences.saveUserId(signInData.idUser.toLong())
    }

    private fun saveUser(signUpData: SignUpData) {
        mutableAuthPreferences.saveAuthToken(signUpData.token)
        mutableAuthPreferences.saveUserId(signUpData.idUser.toLong())
    }
}