package com.example.alien.excent.ui.settings

import com.example.alien.excent.ui.settings.userinformation.UiUserInformation
import javax.inject.Inject

class SettingsUiMapper @Inject
internal constructor(){

    fun toUiUserInformation(email: String, username: String): UiUserInformation {
        return UiUserInformation(username, email)
    }
}