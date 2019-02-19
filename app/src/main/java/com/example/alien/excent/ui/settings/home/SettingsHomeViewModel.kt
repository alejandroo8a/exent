package com.example.alien.excent.ui.settings.home

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.SubjectSupplier
import io.reactivex.Observable
import javax.inject.Inject
import io.reactivex.subjects.PublishSubject

class SettingsHomeViewModel @Inject
internal constructor(
    private val subjectSupplier: SubjectSupplier
) : ViewModel() {

    private val logoutSubject = PublishSubject.create<Boolean>()

    fun logOut() {
        subjectSupplier.emitOnSignOutSubject()
        logoutSubject.onNext(true)
    }

    fun logoutCompleted(): Observable<Boolean> {
        return logoutSubject
    }
}