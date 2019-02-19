package com.example.alien.excent.ui.settings.paymethods

import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_pay_methods)
class PayMethodsFragment : ViewModelFragment<PayMethodsViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = PayMethodsViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    @OnClick(R.id.fb_add_card)
    fun goToAddPayment() {
        navigation.navigateToAction(UiAction.ADD_PAYMENT_METHOD)
    }

    @OnClick(R.id.im_back)
    fun goToBack() {
        navigation.navigateBack()
    }
}
