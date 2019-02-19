package com.example.alien.excent.ui.settings.userinformation.changepassword

import android.support.annotation.VisibleForTesting
import android.support.constraint.ConstraintLayout
import android.view.View
import butterknife.BindView
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.network.LoadingDialogFragment
import com.example.alien.excent.ui.overlay.BaseOverlayActivity
import com.metova.slim.annotation.Layout

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

    public override fun onStop() {
        loadingDialog.dismissAllowingStateLoss()
        super.onStop()
    }
}
