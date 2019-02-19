package com.example.alien.excent.ui.login.signin.passwordforgot

import android.content.Context
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.login.LoginRepository
import com.example.alien.excent.ui.overlay.BaseOverlayViewModel
import com.example.alien.excent.ui.util.NoOpDisposable
import com.example.alien.excent.ui.util.forms.*
import com.example.alien.excent.ui.util.overlay.ScreenShotUtil
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ForgotPasswordOverlayViewModel @Inject
internal constructor(
    context: Context,
    screenShotUtil: ScreenShotUtil,
    private val loginRepository: LoginRepository,
    private val formHelper: FormHelper
) : BaseOverlayViewModel(context, screenShotUtil), FormCallback {

    init {
        initFormHelper()
    }

    private val forgotPasswordSubject = PublishSubject.create<NetworkResult>()
    private var forgotPasswordDisposable: Disposable = NoOpDisposable()
    private val fieldErrorSubject = PublishSubject.create<FormError>()

    private fun initFormHelper() {
        val fieldList = Arrays.asList(
            FormField.USER
        )
        formHelper.initialize(this, fieldList)
    }

    fun forgotPasswordResult(): Observable<NetworkResult> {
        return forgotPasswordSubject
    }

    fun forgotPassword(email : String) {
        forgotPasswordDisposable.dispose()
        forgotPasswordDisposable = loginRepository.forgotPassword(email)
            .doOnError { error ->
                Timber.w(
                    "Error changing password: %s",
                    error.message
                )
            }
            .subscribe(forgotPasswordSubject::onNext)
    }

    fun setShouldShowMessageSuccesForgotPassword() {
        loginRepository.setShouldShowMessageSuccesForgotPassword(true)
    }

    fun validateField(input: FormInput): Boolean {
        return formHelper.validateField(input)
    }

    fun errorUpdates(): Observable<FormError> {
        return fieldErrorSubject
    }

    override fun formError(error: FormError) {
        fieldErrorSubject.onNext(error)
    }
}