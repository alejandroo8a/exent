package com.example.alien.excent

import android.app.Application
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.module.ContextModule
import com.example.alien.excent.module.DaggerApplicationComponent
import com.example.alien.excent.module.NetworkModule

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationComponentHolder.getInstance().setComponent(DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .networkModule(NetworkModule())
                .build())
    }
}