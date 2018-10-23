package com.example.alien.excent.network.adapters

import android.support.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class NullToEmptyStringAdapter {

    val EMPTY_STRING = ""

    @ToJson
    fun toJson(@NullToEmptyString string: String): String {
        return string
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(@Nullable string: String?): String {
        return string ?: EMPTY_STRING
    }
}