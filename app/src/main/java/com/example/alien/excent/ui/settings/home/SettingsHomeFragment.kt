package com.example.alien.excent.ui.settings.home

import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_settings_home)
class SettingsHomeFragment : ViewModelFragment<SettingsHomeViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = SettingsHomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)


}
