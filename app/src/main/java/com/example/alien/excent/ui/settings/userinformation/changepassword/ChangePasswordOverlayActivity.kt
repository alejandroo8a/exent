package com.example.alien.excent.ui.settings.userinformation.changepassword

import android.support.annotation.VisibleForTesting
import android.support.constraint.ConstraintLayout
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.example.alien.excent.R
import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.network.LoadingDialogFragment
import com.example.alien.excent.ui.overlay.BaseOverlayActivity
import com.example.alien.excent.ui.util.forms.FormError
import com.example.alien.excent.ui.util.forms.FormField
import com.example.alien.excent.ui.util.forms.FormInput
import com.metova.slim.annotation.Layout
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_change_password.*

@Layout(R.layout.activity_change_password)
class ChangePasswordOverlayActivity : BaseOverlayActivity<ChangePasswordOverlayViewModel>() {

    @VisibleForTesting
    var loadingDialog = LoadingDialogFragment()

    @BindView(R.id.password_root_view) lateinit var rootView: ConstraintLayout

    override fun getRootViewForBlurredBackground(): View {
        return rootView
    }

    override fun viewModelClass() = ChangePasswordOverlayViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel().changePasswordResult()
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { result -> loadingDialog.loadFinishedObservable(result) }
            .subscribe(this::handleForgotPasswordResult))
        addSubscription(viewModel().errorUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleFieldErrors))
    }

    private fun handleForgotPasswordResult(result : NetworkResult) {
        when (result) {
            NetworkResult.SUCCESS -> {
                viewModel().setShouldShowMessageSuccesChangePassword()
                navigateBack()
            }
            NetworkResult.FORBIDDEN_ERROR -> snackbarUtil.showSnackbar(rootView, R.string.forbidden)
            NetworkResult.CONNECTION_ERROR -> snackbarUtil.showSnackbar(rootView, R.string.connection_error)
            else -> snackbarUtil.showSnackbar(rootView, R.string.generic_request_error)
        }
    }

    private fun handleFieldErrors(fieldError: FormError) {
        snackbarUtil.showLongSnackbar(rootView, getString(fieldError.error, fieldError.fieldText))
    }

    @OnClick(R.id.btn_save)
    fun changePassword() {
        if (viewModel().validateField(FormInput(FormField.PASSWORD, edt_password.text.toString())) &&
            viewModel().validateField(FormInput(FormField.PASSWORD, edt_new_password.text.toString())) &&
            viewModel().validateField(FormInput(FormField.PASSWORD_CONFIRM, edt_new_password_repeat.text.toString(), edt_new_password.text.toString())))
        {
            loadingDialog.show(fragmentManager, null)
            viewModel()!!.changePassword(edt_password.text.toString(), edt_new_password.text.toString())
        }
    }

    public override fun onStop() {
        loadingDialog.dismissAllowingStateLoss()
        super.onStop()
    }

    @OnClick(R.id.im_close)
    fun goToBack() {
        navigateBack()
    }
}
