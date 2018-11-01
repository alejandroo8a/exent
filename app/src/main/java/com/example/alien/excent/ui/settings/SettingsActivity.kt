package com.example.alien.excent.ui.settings

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout

@Layout(R.layout.activity_settings)
class SettingsActivity : ViewModelActivity<SettingsViewModel>() {

    override fun viewModelClass() = SettingsViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToAction(UiAction.SETTINGS_HOME)
    }
}
