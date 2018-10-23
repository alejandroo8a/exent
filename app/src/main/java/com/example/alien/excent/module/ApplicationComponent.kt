package com.example.alien.excent.module

import com.example.alien.excent.ui.login.LoginActivity
import com.example.alien.excent.ui.login.register.RegisterFragment
import com.example.alien.excent.ui.login.signin.SignInFragment
import com.example.alien.excent.ui.splash.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, PreferencesModule::class, NetworkModule::class])
interface ApplicationComponent {

    //Injects

    fun inject(splashScreenActivity: SplashScreenActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(signInFragment: SignInFragment)

    fun inject(registerFragment: RegisterFragment)
}