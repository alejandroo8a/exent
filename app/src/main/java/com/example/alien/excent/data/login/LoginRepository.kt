package com.example.alien.excent.data.login

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.SubjectSupplier
import com.example.alien.excent.data.login.register.SignUpData
import com.example.alien.excent.data.login.signin.SignInData
import com.example.alien.excent.network.login.LoginClient
import io.reactivex.Single
import javax.inject.Inject
import com.example.alien.excent.preferences.auth.MutableAuthPreferences
import dagger.Reusable
import android.annotation.SuppressLint

@Reusable
class LoginRepository @Inject
internal constructor(
    private val mutableAuthPreferences: MutableAuthPreferences, private val client: LoginClient,
    private val mapperData: LoginDataMapper, private val subjectSupplier: SubjectSupplier
) {

    init {
        observeSignOutState()
    }

    private var shouldShowMessageSuccesForgotPassword = false

    fun setShouldShowMessageSuccesForgotPassword(value : Boolean) {
        this.shouldShowMessageSuccesForgotPassword = value
    }

    fun showMessageSuccesForgotPassword (): Boolean {
        return shouldShowMessageSuccesForgotPassword
    }

    fun isSessionActive(): Boolean {
        return mutableAuthPreferences.getUserId() != 0L
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

    fun forgotPassword(email: String) : Single<NetworkResult> {
        return client.forgotPassword(email)
    }

    private fun saveUser(signInData: SignInData) {
        mutableAuthPreferences.saveAuthToken(signInData.token)
        mutableAuthPreferences.saveUserId(signInData.idUser.toLong())
        mutableAuthPreferences.saveUserName(signInData.userName)
        mutableAuthPreferences.saveEmail(signInData.email)
    }

    private fun saveUser(signUpData: SignUpData) {
        mutableAuthPreferences.saveAuthToken(signUpData.token)
        mutableAuthPreferences.saveUserId(signUpData.idUser.toLong())
        mutableAuthPreferences.saveUserName(signUpData.userName)
        mutableAuthPreferences.saveEmail(signUpData.email)
    }

    @SuppressLint("CheckResult")
    private fun observeSignOutState() {
        subjectSupplier.getSignOutSubject()
            .subscribe { mutableAuthPreferences.clearAuthInformation() }
    }
}