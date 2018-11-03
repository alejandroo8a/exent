package com.example.alien.excent.ui.settings.paymethods

import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_pay_methods)
class PayMethodsFragment : ViewModelFragment<PayMethodsViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = PayMethodsViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)
}
