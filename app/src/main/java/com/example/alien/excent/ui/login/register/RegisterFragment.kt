package com.example.alien.excent.ui.login.register

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import butterknife.OnClick
import butterknife.OnEditorAction
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.network.login.UiLoginResult
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.example.alien.excent.ui.navigation.UiAction
import com.example.alien.excent.ui.util.Keyboard
import com.example.alien.excent.ui.util.SnackbarUtil
import com.example.alien.excent.ui.util.forms.FormError
import com.example.alien.excent.ui.util.forms.FormField
import com.example.alien.excent.ui.util.forms.FormInput
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject


@Layout(R.layout.fragment_register)
class RegisterFragment : ViewModelFragment<RegisterViewModel>() {

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Callback
    lateinit var  navigation: Navigation

    override fun viewModelClass() = RegisterViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMaps()
    }

    private fun setUpMaps() {
        edt_user.setTag(R.id.field_type_key, FormField.USER)
        edt_email.setTag(R.id.field_type_key, FormField.EMAIL)
        edt_password.setTag(R.id.field_type_key, FormField.PASSWORD)
        edt_password_confirm.setTag(R.id.field_type_key, FormField.PASSWORD_CONFIRM)
    }

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel()!!.errorUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleFieldErrors))
        addSubscription(viewModel()!!.getSignUpResult()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleRegisterResult))
    }

    private fun handleFieldErrors(fieldError: FormError) {
        snackbarUtil.showLongSnackbar(view!!, getString(fieldError.error, fieldError.fieldText))
    }

    private fun handleRegisterResult(uiLoginResult: UiLoginResult){
        when (uiLoginResult) {
            UiLoginResult.SUCCESS -> navigation.navigateToAction(UiAction.CORE)
            UiLoginResult.INVALID_CREDENTIALS -> snackbarUtil.showSnackbar(view!!, R.string.user_invalid)
            UiLoginResult.FORBIDDEN_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.forbidden)
            UiLoginResult.CONNECTION_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.connection_error)
            UiLoginResult.GENERIC_ERROR -> snackbarUtil.showSnackbar(view!!, R.string.generic_request_error)
        }
    }

    @OnClick(R.id.fab_register)
    fun handleSubmitButtonClick() {
        if (viewModel()!!.validateField(FormInput(FormField.USER, edt_user.text.toString())) &&
            viewModel()!!.validateField(FormInput(FormField.EMAIL, edt_email.text.toString())) &&
            viewModel()!!.validateField(FormInput(FormField.PASSWORD, edt_password.text.toString())) &&
            viewModel()!!.validateField(FormInput(FormField.PASSWORD_CONFIRM, edt_password_confirm.text.toString(), edt_password.text.toString()))
        ) {
            viewModel()!!.submitSignUpInformation(
                edt_user.text.toString(),
                edt_email.text.toString(),
                edt_password.text.toString()
            )
        }
    }

    @OnEditorAction(R.id.edt_password_confirm)
    fun onKeyboardAction(textView: TextView, actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            Keyboard.close(requireActivity(), view!!)
            handleSubmitButtonClick()
            true
        } else {
            false
        }
    }
}
