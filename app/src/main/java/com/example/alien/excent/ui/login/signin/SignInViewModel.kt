package com.example.alien.excent.ui.login.signin

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.login.LoginRepository
import com.example.alien.excent.network.login.signin.UiSignInResult
import com.example.alien.excent.ui.util.NoOpDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class SignInViewModel @Inject
internal constructor(private val loginRepository: LoginRepository, private val uiMapper: LoginUiMapper) : ViewModel() {

    private val resultSubject = PublishSubject.create<UiSignInResult>()
    private var disposable: Disposable = NoOpDisposable()

    fun getLoginResult(): Observable<UiSignInResult> {
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