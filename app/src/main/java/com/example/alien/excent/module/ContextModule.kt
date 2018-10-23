package com.example.alien.excent.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    private val context: Context

    constructor(context: Context) {
        this.context = context
    }

    @Provides
    @Singleton
    fun providesContext() : Context {
        return context
    }
}