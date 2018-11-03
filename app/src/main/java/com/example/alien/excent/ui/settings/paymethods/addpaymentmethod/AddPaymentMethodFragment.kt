package com.example.alien.excent.ui.settings.paymethods.addpaymentmethod

import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_add_payment_method)
class AddPaymentMethodFragment : ViewModelFragment<AddPaymentMethodViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = AddPaymentMethodViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)



}
