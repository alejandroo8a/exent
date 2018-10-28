package com.example.alien.excent.ui.core

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout

@Layout(R.layout.activity_core)
class CoreActivity : ViewModelActivity<CoreViewModel>() {

    override fun viewModelClass() = CoreViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToAction(UiAction.HOME)
    }
}
