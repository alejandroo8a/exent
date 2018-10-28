package com.example.alien.excent.ui.login.register

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.login.LoginRepository
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.login.LoginUiMapper
import com.example.alien.excent.ui.util.NoOpDisposable
import com.example.alien.excent.ui.util.forms.FormCallback
import com.example.alien.excent.ui.util.forms.FormError
import com.example.alien.excent.ui.util.forms.FormField
import com.example.alien.excent.ui.util.forms.FormHelper
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import com.example.alien.excent.ui.util.forms.FormInput


class RegisterViewModel @Inject
internal constructor(
    private val loginRepository: LoginRepository,
    private val uiMapper: LoginUiMapper,
    private val formHelper: FormHelper
) : ViewModel(), FormCallback{

    init {
        initFormHelper()
    }

    private val resultSubject = PublishSubject.create<UiLoginResult>()
    private var disposable: Disposable = NoOpDisposable()

    private val fieldErrorSubject = PublishSubject.create<FormError>()

    private fun initFormHelper() {
        val fieldList = Arrays.asList(
            FormField.USER,
            FormField.EMAIL,
            FormField.PASSWORD,
            FormField.PASSWORD_CONFIRM
        )
        formHelper.initialize(this, fieldList)
    }

    fun getSignUpResult(): Observable<UiLoginResult> {
        return resultSubject
    }

    fun validateField(input: FormInput): Boolean {
        return formHelper.validateField(input)
    }

    fun errorUpdates(): Observable<FormError> {
        return fieldErrorSubject
    }

    fun submitSignUpInformation(user: String, email: String, password: String) {
        disposable.dispose()
        disposable = loginRepository.submitNewUser(user, email, password)
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