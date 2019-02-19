package com.example.alien.excent.ui.login.signin

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import butterknife.OnClick
import butterknife.OnEditorAction
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.base.ViewModelFragment
import com.metova.slim.annotation.Layout
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.util.Keyboard
import com.metova.slim.annotation.Callback
import com.example.alien.excent.ui.util.SnackbarUtil
import com.example.alien.excent.ui.util.forms.FormError
import com.example.alien.excent.ui.util.forms.FormField
import com.example.alien.excent.ui.util.forms.FormInput
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

        addSubscription(viewModel()!!.errorUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleFieldErrors))
        addSubscription(viewModel()!!.getLoginResult()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleLoginResult))
        handleShouldShowMessageSuccesForgotPassword(viewModel()!!.getShouldShowMessageSuccesForgotPassword())
    }

    private fun handleShouldShowMessageSuccesForgotPassword(value : Boolean) {
        if(value) {
            snackbarUtil.showSnackbar(view!!, R.string.verify_email)
            viewModel()!!.setShouldShowMessageSuccesForgotPassword()
        }
    }

    private fun handleLoginResult(uiLoginResult: UiLoginResult){
        when (uiLoginResult) {
            UiLoginResult.SUCCESS -> navigation.navigateToAction(UiAction.CORE)
            UiLoginResult.INVALID_CREDENTIALS -> snackbarUtil.showSnackbar(view!!, R.string.user_invalid)
            UiLoginResult.FORBIDDEN_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.forbidden)
            UiLoginResult.CONNECTION_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.connection_error)
            UiLoginResult.GENERIC_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.generic_request_error)
        }
    }

    private fun handleFieldErrors(fieldError: FormError) {
        snackbarUtil.showLongSnackbar(view!!, getString(fieldError.error, fieldError.fieldText))
    }

    @OnClick(R.id.fab_login)
    fun submitLoginInformation() {
        if (viewModel()!!.validateField(FormInput(FormField.EMAIL, edt_user.text.toString())) &&
            viewModel()!!.validateField(FormInput(FormField.PASSWORD, edt_password.text.toString()))
        ) {
            viewModel()!!.submitLoginInformation(
                edt_user.text.toString(),
                edt_password.text.toString()
            )
        }
    }

    @OnClick(R.id.btn_register)
    fun goToRegister() {
        navigation.navigateToAction(UiAction.REGISTER)
    }

    @OnClick(R.id.txt_forgot_password)
    fun goToForgotPassword() {
        navigation.navigateToAction(UiAction.FORGOT_PASSWORD)
    }

    @OnEditorAction(R.id.edt_password)
    fun onKeyboardAction(textView: TextView, actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            Keyboard.close(requireActivity(), view!!)
            submitLoginInformation()
            true
        } else {
            false
        }
    }
}
