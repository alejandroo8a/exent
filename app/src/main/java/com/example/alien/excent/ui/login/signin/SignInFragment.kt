package com.example.alien.excent.ui.login.signin

import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.base.ViewModelFragment
import com.metova.slim.annotation.Layout
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Callback
import com.example.alien.excent.ui.util.SnackbarUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject


@Layout(R.layout.fragment_sign_in)
class SignInFragment : ViewModelFragment<SignInViewModel>() {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = SignInViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)


    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel()!!.getLoginResult()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleLoginResult))

    }

    private fun handleLoginResult(uiLoginResult: UiLoginResult){
        when (uiLoginResult) {
            UiLoginResult.SUCCESS -> snackbarUtil.showSnackbar(view!!, "Exito :D")// navigation.navigateToAction(UiAction.REGISTER)
            UiLoginResult.INVALID_CREDENTIALS -> snackbarUtil.showSnackbar(view!!, R.string.user_invalid)
            UiLoginResult.FORBIDDEN_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.forbidden)
            UiLoginResult.CONNECTION_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.connection_error)
            UiLoginResult.GENERIC_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.generic_request_error)
        }
    }

    private fun verifyFieldsLogin(): Boolean {
        return edt_user.text.toString().isNotEmpty() && edt_password!!.text.toString().isNotEmpty()
    }

    @OnClick(R.id.fab_login)
    fun submitLoginInformation() {
        if(verifyFieldsLogin()) {
            viewModel()!!.submitLoginInformation(edt_user.text.toString(), edt_password.text.toString())
        } else {
            snackbarUtil.showSnackbar(view!!, R.string.complete_fields_login)
        }
    }

    @OnClick(R.id.btn_register)
    fun goToRegister() {
        navigation.navigateToAction(UiAction.REGISTER)
    }
}
