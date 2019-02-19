package com.example.alien.excent.ui.settings.userinformation

import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user_information.*

@Layout(R.layout.fragment_user_information)
class UserInformationFragment : ViewModelFragment<UserInformationViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = UserInformationViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(
            viewModel()!!.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::populateView)
        )
    }

    private fun populateView(userInformation: UiUserInformation) {
        edt_user.setText(userInformation.username)
        edt_email.setText(userInformation.email)
        edt_user.isEnabled = false
        edt_email.isEnabled = false
    }

    @OnClick(R.id.btn_change_password)
    fun goToChangePassword() {
        navigation.navigateToAction(UiAction.CHANGE_PASSWORD)
    }

    @OnClick(R.id.im_back)
    fun goToBack() {
        navigation.navigateBack()
    }
}
