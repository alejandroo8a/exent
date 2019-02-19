package com.example.alien.excent.ui.settings.home

import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers

@Layout(R.layout.fragment_settings_home)
class SettingsHomeFragment : ViewModelFragment<SettingsHomeViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = SettingsHomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel()!!.logoutCompleted()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { navigation.navigateToAction(UiAction.LOGIN) })
    }

    @OnClick(R.id.ss_payment_method)
    fun goToPaymentMethod() {
        navigation.navigateToAction(UiAction.PAYMENT_METHOD)
    }

    @OnClick(R.id.ss_user_information)
    fun goToUserInformation() {
        navigation.navigateToAction(UiAction.USER_INFORMATION)
    }

    @OnClick(R.id.txt_exit)
    fun signOut() {
        viewModel()!!.logOut()
    }

    @OnClick(R.id.im_back)
    fun goToBack() {
        navigation.navigateBack()
    }
}
