package com.example.alien.excent.ui.login.signin

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.login.LoginRepository
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.login.LoginUiMapper
import com.example.alien.excent.ui.util.NoOpDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class SignInViewModel @Inject
internal constructor(
    private val loginRepository: LoginRepository,
    private val uiMapper: LoginUiMapper
) : ViewModel() {

    private val resultSubject = PublishSubject.create<UiLoginResult>()
    private var disposable: Disposable = NoOpDisposable()

    fun getLoginResult(): Observable<UiLoginResult> {
        return resultSubject
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

}