package com.example.alien.excent.ui.settings.userinformation.changepassword

import android.content.Context
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.user.UserRepository
import com.example.alien.excent.ui.overlay.BaseOverlayViewModel
import com.example.alien.excent.ui.util.NoOpDisposable
import com.example.alien.excent.ui.util.forms.*
import com.example.alien.excent.ui.util.overlay.ScreenShotUtil
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ChangePasswordOverlayViewModel @Inject
internal constructor(
    context: Context,
    screenShotUtil: ScreenShotUtil,
    private val userRepository: UserRepository,
    private val formHelper: FormHelper
) : BaseOverlayViewModel(context, screenShotUtil), FormCallback {

    init {
        initFormHelper()
    }
    private val changePasswordSubject = PublishSubject.create<NetworkResult>()
    private var changePasswordDisposable: Disposable = NoOpDisposable()
    private val fieldErrorSubject = PublishSubject.create<FormError>()

    private fun initFormHelper() {
        val fieldList = Arrays.asList(
            FormField.PASSWORD,
            FormField.PASSWORD_CONFIRM
        )
        formHelper.initialize(this, fieldList)
    }

    fun changePasswordResult(): Observable<NetworkResult> {
        return changePasswordSubject
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        changePasswordDisposable.dispose()
        changePasswordDisposable = userRepository.changePassword(oldPassword, newPassword)
            .doOnError { error ->
                Timber.w(
                    "Error changing password: %s",
                    error.message
                )
            }
            .subscribe(changePasswordSubject::onNext)

    }

    fun setShouldShowMessageSuccesChangePassword() {
        userRepository.setShouldShowMessageSuccesChangePassword(true)
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