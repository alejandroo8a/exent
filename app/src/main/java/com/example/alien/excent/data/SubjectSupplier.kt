package com.example.alien.excent.data

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectSupplier @Inject
constructor() {

    private val SignOutSubject = PublishSubject.create<Boolean>()
    private val expiredSessionSubject = PublishSubject.create<Boolean>()

    fun getSignOutSubject(): Observable<Boolean> {
        return SignOutSubject
    }

    fun emitOnSignOutSubject() {
        SignOutSubject.onNext(true)
    }

    fun getExpiredSessionSubject(): Observable<Boolean> {
        return expiredSessionSubject
    }

    fun emitExpiredSessionSubject() {
        expiredSessionSubject.onNext(true)
    }
}