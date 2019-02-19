package com.example.alien.excent.data.user

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.user.UserClient
import com.example.alien.excent.preferences.auth.MutableAuthPreferences
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class UserRepository @Inject
internal constructor(
    private val mutableAuthPreferences: MutableAuthPreferences,
    private val userClient: UserClient
) {

    private var shouldShowMessageSuccesChangePassword = false

    fun setShouldShowMessageSuccesChangePassword(value : Boolean) {
        this.shouldShowMessageSuccesChangePassword = value
    }

    fun showMessageSuccesChangePassword (): Boolean {
        return shouldShowMessageSuccesChangePassword
    }
    fun getUserName(): Single<String> {
        return Single.just(mutableAuthPreferences.getUserName())
    }

    fun getEmail(): Single<String> {
        return Single.just(mutableAuthPreferences.getEmail())
    }

    fun changePassword(oldPassword: String, newPassword: String): Single<NetworkResult>{
        return userClient.changePassword(oldPassword, newPassword)
    }
}