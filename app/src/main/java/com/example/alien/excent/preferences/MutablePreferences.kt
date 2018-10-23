package com.example.alien.excent.preferences

import android.content.SharedPreferences
import javax.inject.Inject


class MutablePreferences @Inject
internal constructor(sharedPreferences: SharedPreferences) : Preferences(sharedPreferences) {

    // region save

    fun save(key: String, value: Boolean) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply()
    }

    fun save(key: String, value: Float) {
        sharedPreferences.edit()
                .putFloat(key, value)
                .apply()
    }

    fun save(key: String, value: Int) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply()
    }

    fun save(key: String, value: Long) {
        sharedPreferences.edit()
                .putLong(key, value)
                .apply()
    }

    fun save(key: String, value: String) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply()
    }

    // end region

    fun remove(key: String) {
        sharedPreferences.edit()
                .remove(key)
                .apply()
    }

}