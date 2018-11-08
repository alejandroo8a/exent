package com.example.alien.excent.ui.settings.userinformation

import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_user_information)
class UserInformationFragment : ViewModelFragment<UserInformationViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = UserInformationViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    @OnClick(R.id.btn_change_password)
    fun goToChangePassword() {
        navigation.navigateToAction(UiAction.CHANGE_PASSWORD)
    }
}
