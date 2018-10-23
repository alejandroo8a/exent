package com.example.alien.excent.preferences

import android.content.SharedPreferences
import javax.inject.Inject

open class Preferences @Inject
internal constructor(protected val sharedPreferences: SharedPreferences) {

    fun retrieve(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun retrieve(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun retrieve(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun retrieve(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun retrieve(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)
    }
}