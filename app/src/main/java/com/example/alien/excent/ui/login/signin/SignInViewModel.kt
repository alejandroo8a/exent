package com.example.alien.excent.ui.login.signin

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.login.LoginRepository
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.login.LoginUiMapper
import com.example.alien.excent.ui.util.NoOpDisposable
import com.example.alien.excent.ui.util.forms.*
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

class SignInViewModel @Inject
internal constructor(
    private val loginRepository: LoginRepository,
    private val uiMapper: LoginUiMapper,

    private val formHelper: FormHelper
) : ViewModel(), FormCallback {

    init {
        initFormHelper()
    }

    private val resultSubject = PublishSubject.create<UiLoginResult>()
    private var disposable: Disposable = NoOpDisposable()

    private val fieldErrorSubject = PublishSubject.create<FormError>()

    fun getLoginResult(): Observable<UiLoginResult> {
        return resultSubject
    }

    private fun initFormHelper() {
        val fieldList = Arrays.asList(
            FormField.USER,
            FormField.PASSWORD
        )
        formHelper.initialize(this, fieldList)
    }

    fun validateField(input: FormInput): Boolean {
        return formHelper.validateField(input)
    }

    fun errorUpdates(): Observable<FormError> {
        return fieldErrorSubject
    }

    fun submitLoginInformation(user: String, password: String) {
        disposable.dispose()
        disposable = loginRepository.submitLoginInformation(user, password)
            .map(uiMapper::toUiSignInResult)
            .doOnError { error ->
                Timber.w(
                    "Error submitting login information: %s",
                    error.message
                )
            }
            .subscribe(resultSubject::onNext)
    }

    override fun formError(error: FormError) {
        fieldErrorSubject.onNext(error)
    }
}