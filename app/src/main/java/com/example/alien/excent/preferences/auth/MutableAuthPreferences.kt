package com.example.alien.excent.preferences.auth

import com.example.alien.excent.preferences.MutablePreferences
import javax.inject.Inject

class MutableAuthPreferences @Inject
internal constructor(private val mutablePreferences: MutablePreferences) : AuthPreferences(mutablePreferences){

    fun saveUserId(userId: Long) {
        mutablePreferences.save(AuthPreferences.Keys.USER_ID, userId)
    }

    fun saveAuthToken(token: String) {
        mutablePreferences.save(AuthPreferences.Keys.TOKEN, token)
    }

    fun saveUserName(userName: String) {
        mutablePreferences.save(AuthPreferences.Keys.USER_NAME, userName)
    }

    fun saveEmail(email: String) {
        mutablePreferences.save(AuthPreferences.Keys.EMAIL, email)
    }

    fun clearAuthInformation() {
        mutablePreferences.remove(AuthPreferences.Keys.USER_ID)
        mutablePreferences.remove(AuthPreferences.Keys.TOKEN)
        mutablePreferences.remove(AuthPreferences.Keys.USER_NAME)
        mutablePreferences.remove(AuthPreferences.Keys.EMAIL)
    }
}