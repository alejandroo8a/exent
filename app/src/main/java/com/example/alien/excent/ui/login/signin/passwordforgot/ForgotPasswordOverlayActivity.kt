package com.example.alien.excent.ui.login.signin.passwordforgot

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
import kotlinx.android.synthetic.main.activity_forgot_password_overlay.*

@Layout(R.layout.activity_forgot_password_overlay)
class ForgotPasswordOverlayActivity : BaseOverlayActivity<ForgotPasswordOverlayViewModel>() {

    @VisibleForTesting
    var loadingDialog = LoadingDialogFragment()

    @BindView(R.id.forgot_password_root_view) lateinit var rootView: ConstraintLayout

    override fun getRootViewForBlurredBackground(): View {
        return rootView
    }

    override fun viewModelClass() = ForgotPasswordOverlayViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun subscribeOnStart() {
        super.subscribeOnStart()

        addSubscription(viewModel().forgotPasswordResult()
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
                viewModel().setShouldShowMessageSuccesForgotPassword()
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

    @OnClick(R.id.btn_send_email)
    fun forgotPassword() {
        if (viewModel().validateField(FormInput(FormField.EMAIL, edt_email.text.toString()))) {
            loadingDialog.show(fragmentManager, null)
            viewModel()!!.forgotPassword(edt_email.text.toString())
        }
    }

    @OnClick(R.id.im_close)
    fun goBack() {
        navigateBack()
    }

    public override fun onStop() {
        loadingDialog.dismissAllowingStateLoss()
        super.onStop()
    }
}
