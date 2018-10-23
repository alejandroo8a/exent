package com.example.alien.excent.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import dagger.Module
import dagger.Provides
import java.lang.UnsupportedOperationException

@Module(includes = arrayOf(ContextModule::class))
class PreferencesModule {

    fun PreferencesModule() {
        throw UnsupportedOperationException()
    }

    @Module(includes = arrayOf(ContextModule::class))
    companion object {
        val PREFERENCES_NAME = "com.dev.excent#main-preferences"

        @JvmStatic
        @Provides
        fun providesSharedPreferences(context : Context) : SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }
}