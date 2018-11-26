package com.example.alien.excent.ui.base

import android.app.ActivityOptions
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.alien.excent.R
import com.example.alien.excent.ui.core.CoreActivity
import com.example.alien.excent.ui.core.home.HomeFragment
import com.example.alien.excent.ui.core.home.UiEvents
import com.example.alien.excent.ui.event.EventActivity
import com.example.alien.excent.ui.event.core.EventCoreFragment
import com.example.alien.excent.ui.login.LoginActivity
import com.example.alien.excent.ui.login.register.RegisterFragment
import com.example.alien.excent.ui.login.signin.SignInFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.settings.SettingsActivity
import com.example.alien.excent.ui.settings.home.SettingsHomeFragment
import com.example.alien.excent.ui.settings.paymethods.PayMethodsFragment
import com.example.alien.excent.ui.settings.paymethods.addpaymentmethod.AddPaymentMethodFragment
import com.example.alien.excent.ui.settings.userinformation.UserInformationFragment
import com.example.alien.excent.ui.settings.userinformation.changepassword.ChangePasswordOverlayActivity
import com.example.alien.excent.ui.typeevents.TypeEventsActivity
import com.example.alien.excent.ui.typeevents.home.TypeEventsHomeFragment
import com.example.alien.excent.ui.util.SnackbarUtil
import com.example.alien.excent.ui.util.ToastUtil
import javax.inject.Inject
import com.example.alien.excent.ui.overlay.BaseOverlayActivity
import com.example.alien.excent.ui.util.overlay.ScreenShotUtil
import java.io.Serializable


abstract class NavigationActivity: BaseActivity(), Navigation {

    val KEY_NEXT_NAVIVATION_ACTION = "navigation_action"

    @Inject protected lateinit var snackbarUtil: SnackbarUtil
    @Inject protected lateinit var toastUtil: ToastUtil

    @Inject protected lateinit var screenShotUtil: ScreenShotUtil

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        inject()
    }

    protected abstract fun inject()

    override fun navigateBack() {
        onBackPressed()
    }

    override fun finishActivity() {
        finish()
    }

    override fun navigateToAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.ADD_PAYMENT_METHOD -> {
                verifyActivity(SettingsActivity::class.java)
                launchFragment(AddPaymentMethodFragment())
            }
            UiAction.CHANGE_PASSWORD -> {
                prepareToLaunchOverlay()
                launchActivity(ChangePasswordOverlayActivity::class.java)
            }
            UiAction.CORE -> launchActivityInClearTask(CoreActivity::class.java)
            UiAction.HOME -> {
                verifyActivity(CoreActivity::class.java)
                launchFragment(HomeFragment())
            }
            UiAction.LOGIN -> launchActivityInClearTask(LoginActivity::class.java)
            UiAction.PAYMENT_METHOD -> {
                verifyActivity(SettingsActivity::class.java)
                launchFragment(PayMethodsFragment())
            }
            UiAction.REGISTER -> {
                verifyActivity(LoginActivity::class.java)
                launchFragment(RegisterFragment())
            }
            UiAction.SETTINGS -> launchActivity(SettingsActivity::class.java)
            UiAction.SETTINGS_HOME -> {
                verifyActivity(SettingsActivity::class.java)
                launchFragment(SettingsHomeFragment())
            }
            UiAction.SIGN_IN -> {
                verifyActivity(LoginActivity::class.java)
                launchFragment(SignInFragment())
            }
            UiAction.TYPE_EVENT -> launchActivity(TypeEventsActivity::class.java)
            UiAction.TYPE_EVENT_HOME -> {
                verifyActivity(TypeEventsActivity::class.java)
                launchFragment(TypeEventsHomeFragment())
            }
            UiAction.USER_INFORMATION -> {
                verifyActivity(SettingsActivity::class.java)
                launchFragment(UserInformationFragment())
            }
            else -> {
                showGenericSnackbar(R.string.launch_action_not_supported)
            }
        }
    }

    override fun <T> navigateToAction(action: UiAction, argument: T, nameArgument: String) {
        when (action) {
            UiAction.EVENT -> {
                if (argument is UiEvents) {
                    launchActivityWithArguments(EventActivity::class.java, argument, nameArgument)
                } else {
                    throw IllegalArgumentException()
                }
            }
            UiAction.EVENT_CORE -> {
                if (argument is UiEvents) {
                    launchFragment(EventCoreFragment.newInstance(argument as UiEvents))
                } else {
                    throw IllegalArgumentException()
                }
            }
            else -> {
                showGenericSnackbar(R.string.launch_action_not_supported)
            }
        }
    }

    private fun showGenericSnackbar(@StringRes message: Int) {
        snackbarUtil.showSnackbar(findViewById(android.R.id.content), message)
    }

    private fun <T> launchActivityWithArguments(activity: Class<out AppCompatActivity>, argument: T, nameArgument : String){
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(nameArgument, argument as Serializable)
        launchActivityIntent(intent)
    }

    private fun launchActivity(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        launchActivityIntent(intent)
    }

    private fun launchActivityIntentSingleTop(intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        launchActivityIntent(intent)
    }

    private fun launchActivityInClearTask(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        launchActivityIntent(intent)
    }

    private fun launchActivityIntent(intent: Intent) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun launchFragment(fragment: Fragment) {
        launchFragment(fragment, true)
    }

    private fun launchFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val isFirstFragment = fragmentManager.fragments.isEmpty()
        val ft = fragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.replace(android.R.id.content, fragment, fragment.tag)
        if (addToBackStack && !isFirstFragment) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }

        ft.commit()
    }

    private fun verifyActivity(activityClass: Class<out AppCompatActivity>) {
        if( !activityClass.isInstance(this)){
            throw IllegalStateException("Launching fragment is not avaliable from this activity")
        }
    }

    private fun prepareToLaunchOverlay() {
        if (this !is BaseOverlayActivity<*>) {
            screenShotUtil.takeScreenShot(this)
        }
    }
}