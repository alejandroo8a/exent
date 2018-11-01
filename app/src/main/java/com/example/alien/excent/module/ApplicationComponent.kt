package com.example.alien.excent.module

import com.example.alien.excent.ui.core.CoreActivity
import com.example.alien.excent.ui.core.home.HomeFragment
import com.example.alien.excent.ui.login.LoginActivity
import com.example.alien.excent.ui.login.register.RegisterFragment
import com.example.alien.excent.ui.login.signin.SignInFragment
import com.example.alien.excent.ui.network.LoadingDialogFragment
import com.example.alien.excent.ui.settings.SettingsActivity
import com.example.alien.excent.ui.settings.home.SettingsHomeFragment
import com.example.alien.excent.ui.splash.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, PreferencesModule::class, NetworkModule::class])
interface ApplicationComponent {

    //Injects

    fun inject(coreActivity: CoreActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(loginActivity: LoginActivity)

    fun inject(registerFragment: RegisterFragment)

    fun inject(signInFragment: SignInFragment)

    fun inject(splashScreenActivity: SplashScreenActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(settingsHomeFragment: SettingsHomeFragment)

    fun inject(loadingDialogFragment: LoadingDialogFragment)

}