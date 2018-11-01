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
import com.example.alien.excent.ui.login.LoginActivity
import com.example.alien.excent.ui.login.register.RegisterFragment
import com.example.alien.excent.ui.login.signin.SignInFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.settings.SettingsActivity
import com.example.alien.excent.ui.settings.home.SettingsHomeFragment
import com.example.alien.excent.ui.util.SnackbarUtil
import com.example.alien.excent.ui.util.ToastUtil
import javax.inject.Inject


abstract class NavigationActivity: BaseActivity(), Navigation {

    val KEY_NEXT_NAVIVATION_ACTION = "navigation_action"

    @Inject protected lateinit var snackbarUtil: SnackbarUtil
    @Inject protected lateinit var toastUtil: ToastUtil

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
        when(uiAction) {
            UiAction.CORE -> launchActivityInClearTask(CoreActivity::class.java)
            UiAction.HOME -> {
                verifyActivity(CoreActivity::class.java)
                launchFragment(HomeFragment())
            }
            UiAction.LOGIN -> launchActivityInClearTask(LoginActivity::class.java)
            UiAction.SIGN_IN -> {
                verifyActivity(LoginActivity::class.java)
                launchFragment(SignInFragment())
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
        }
    }

    private fun showGenericSnackbar(@StringRes message: Int) {
        snackbarUtil.showSnackbar(findViewById(android.R.id.content), message)
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
}