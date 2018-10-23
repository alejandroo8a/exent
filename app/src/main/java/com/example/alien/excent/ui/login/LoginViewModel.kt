package com.example.alien.excent.ui.login

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.login.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginViewModel @Inject
internal constructor(private val loginRepository: LoginRepository) : ViewModel() {

    fun sessionActive(): Single<Boolean> {
        return Single.just(loginRepository.isSessionActive())
    }
}