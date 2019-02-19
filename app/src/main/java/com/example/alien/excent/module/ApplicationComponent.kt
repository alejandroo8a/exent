package com.example.alien.excent.module

import com.example.alien.excent.ui.core.CoreActivity
import com.example.alien.excent.ui.core.home.HomeFragment
import com.example.alien.excent.ui.event.EventActivity
import com.example.alien.excent.ui.event.core.EventCoreFragment
import com.example.alien.excent.ui.login.LoginActivity
import com.example.alien.excent.ui.login.register.RegisterFragment
import com.example.alien.excent.ui.login.signin.SignInFragment
import com.example.alien.excent.ui.login.signin.passwordforgot.ForgotPasswordOverlayActivity
import com.example.alien.excent.ui.network.LoadingDialogFragment
import com.example.alien.excent.ui.settings.SettingsActivity
import com.example.alien.excent.ui.settings.home.SettingsHomeFragment
import com.example.alien.excent.ui.settings.paymethods.PayMethodsFragment
import com.example.alien.excent.ui.settings.paymethods.addpaymentmethod.AddPaymentMethodFragment
import com.example.alien.excent.ui.settings.userinformation.UserInformationFragment
import com.example.alien.excent.ui.settings.userinformation.changepassword.ChangePasswordOverlayActivity
import com.example.alien.excent.ui.splash.SplashScreenActivity
import com.example.alien.excent.ui.typeevents.TypeEventsActivity
import com.example.alien.excent.ui.typeevents.home.TypeEventsHomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, PreferencesModule::class, NetworkModule::class])
interface ApplicationComponent {

    //Injects

    fun inject(addPaymentMethodFragment: AddPaymentMethodFragment)

    fun inject(changePasswordOverlayActivity: ChangePasswordOverlayActivity)

    fun inject(forgotPasswordOverlayActivity: ForgotPasswordOverlayActivity)

    fun inject(coreActivity: CoreActivity)

    fun inject(eventActivity: EventActivity)

    fun inject(eventCoreFragment: EventCoreFragment)

    fun inject(homeFragment: HomeFragment)

    fun inject(loadingDialogFragment: LoadingDialogFragment)

    fun inject(loginActivity: LoginActivity)

    fun inject(payMethodsFragment: PayMethodsFragment)

    fun inject(registerFragment: RegisterFragment)

    fun inject(signInFragment: SignInFragment)

    fun inject(splashScreenActivity: SplashScreenActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(settingsHomeFragment: SettingsHomeFragment)

    fun inject(typeEventsActivity: TypeEventsActivity)

    fun inject(typeEventsHomeFragment: TypeEventsHomeFragment)

    fun inject(userInformationFragment: UserInformationFragment)
}