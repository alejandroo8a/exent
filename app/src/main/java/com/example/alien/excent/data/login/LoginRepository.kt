package com.example.alien.excent.data.login

import com.example.alien.excent.data.login.signin.LoginDataMapper
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

    private fun saveUser(signInData: SignInData) {
        mutableAuthPreferences.saveAuthToken(signInData.token)
        mutableAuthPreferences.saveUserId(signInData.idUser.toLong())
    }
}