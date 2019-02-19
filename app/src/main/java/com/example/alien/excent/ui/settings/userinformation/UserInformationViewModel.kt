package com.example.alien.excent.ui.settings.userinformation

import android.arch.lifecycle.ViewModel
import com.example.alien.excent.data.user.UserRepository
import com.example.alien.excent.ui.settings.SettingsUiMapper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

class UserInformationViewModel @Inject
internal constructor(
    private val userRepository: UserRepository,
    private val mapper: SettingsUiMapper
) : ViewModel(){

    fun getUserInfo(): Single<UiUserInformation> {
        return Single.zip(
            userRepository.getEmail(),
            userRepository.getUserName(),
            BiFunction { email, username -> mapper.toUiUserInformation(email, username) })
    }

    fun setShouldShowMessageSuccesChangePassword() {
        userRepository.setShouldShowMessageSuccesChangePassword(false)
    }

    fun showMessageSuccesChangePassword(): Boolean {
        return userRepository.showMessageSuccesChangePassword()
    }
}