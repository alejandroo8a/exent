package com.example.alien.excent.preferences.auth

import com.example.alien.excent.preferences.Preferences
import javax.inject.Inject



open class AuthPreferences @Inject
internal constructor(private val preferences: Preferences){

    fun getUserId(): Long {
        return preferences.retrieve(Keys.USER_ID, Defaults.USER_ID)
    }
    fun getAuthToken(): String {
        return preferences.retrieve(Keys.TOKEN, Defaults.TOKEN)
    }

    internal class Keys private constructor() {

        init {
            throw UnsupportedOperationException()
        }

        companion object {
            private const val PREFIX = "AuthPreferences.Keys#"

            const val USER_ID = PREFIX + "USER_ID"
            const val TOKEN = PREFIX + "TOKEN"
        }
    }

    internal class Defaults private constructor() {

        init {
            throw UnsupportedOperationException()
        }

        companion object {
            internal const val USER_ID: Long = 0
            internal const val TOKEN: String = ""
        }
    }
}