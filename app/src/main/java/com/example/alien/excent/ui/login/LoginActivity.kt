package com.example.alien.excent.ui.login

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers

@Layout(R.layout.activity_login)
class LoginActivity: ViewModelActivity<LoginViewModel>() {

    override fun viewModelClass() = LoginViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToAction(UiAction.SIGN_IN)
    }

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel().sessionActive()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ sessionActive ->
                    if(sessionActive) {
                        navigateToAction(UiAction.CORE)
                    }
                })
    }
}