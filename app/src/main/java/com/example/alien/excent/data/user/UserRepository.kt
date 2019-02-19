package com.example.alien.excent.data.user

import com.example.alien.excent.preferences.auth.MutableAuthPreferences
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject
internal constructor(
    private val mutableAuthPreferences: MutableAuthPreferences
) {
    fun getUserName(): Single<String> {
        return Single.just(mutableAuthPreferences.getUserName())
    }

    fun getEmail(): Single<String> {
        return Single.just(mutableAuthPreferences.getEmail())
    }
}