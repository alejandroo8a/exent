package com.example.alien.excent.ui.overlay

import android.os.Bundle
import android.view.View
import com.example.alien.excent.ui.base.ViewModelActivity
import io.reactivex.android.schedulers.AndroidSchedulers



abstract class BaseOverlayActivity<VM : BaseOverlayViewModel> : ViewModelActivity<VM>(){

    val EXTRA_OVERLAY_TYPE = "overlay_type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBackgroundImage()
    }

    protected fun getTypeFromIntent(): Enum<*> {
        if (intent.extras != null && intent.extras!!.containsKey(EXTRA_OVERLAY_TYPE)) {
            return intent.extras!!.get(EXTRA_OVERLAY_TYPE) as Enum<*>
        }

        throw UnsupportedOperationException("Overlay activity requires a type specification")
    }

    private fun loadBackgroundImage() {
        addSubscription(viewModel().getBlurredBackground()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { backgroundDrawable ->
                getRootViewForBlurredBackground().background = backgroundDrawable
            })
    }

    protected abstract fun getRootViewForBlurredBackground(): View
}
