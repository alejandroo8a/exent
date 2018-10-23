package com.example.alien.excent.data

import io.reactivex.subjects.CompletableSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SubjectSupplier @Inject
constructor() {

    var logoutSubject: CompletableSubject? = null
        private set

    init {
        resetLogoutState()
    }

    fun resetLogoutState() {
        Timber.d("Resetting logout state.")
        logoutSubject = CompletableSubject.create()
    }
}