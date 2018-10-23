package com.example.alien.excent.ui.splash

import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@Layout(R.layout.activity_splash_screen)
class SplashScreenActivity: ViewModelActivity<SplashScreenViewModel>() {

    override fun viewModelClass() = SplashScreenViewModel::class.java

    override fun inject() = ApplicationComponentHolder.getInstance().getComponent().inject(this)

    override fun subscribeOnStart(){
        addSubscription(Completable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::launchLoginAcitivity))
    }

    private fun launchLoginAcitivity() {
        navigateToAction(UiAction.LOGIN)
    }
}