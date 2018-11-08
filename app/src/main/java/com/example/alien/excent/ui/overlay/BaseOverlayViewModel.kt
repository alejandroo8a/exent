package com.example.alien.excent.ui.overlay

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.graphics.Bitmap
import com.example.alien.excent.ui.util.overlay.ScreenShotUtil
import javax.inject.Inject
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.alien.excent.ui.util.overlay.BlurBuilder
import io.reactivex.Single
import timber.log.Timber




open class BaseOverlayViewModel @Inject
internal constructor(
    @SuppressLint("StaticFieldLeak") private val context: Context,
    private val screenShotUtil: ScreenShotUtil
) : ViewModel() {

    private fun getErrorBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        bitmap.setPixel(
            0,
            0,
            ResourcesCompat.getColor(context.resources, android.R.color.white, null)
        )
        return bitmap
    }

    fun getBlurredBackground(): Single<Drawable> {
        var bitmap = getBlurredScreenshot()
        if (bitmap == null) {
            Timber.d("Screenshot bitmap is null, using error bitmap instead.")
            bitmap = getErrorBitmap()
        }
        val background = BitmapDrawable(context.resources, bitmap)
        return Single.just(background)
    }

    private fun getBlurredScreenshot(): Bitmap? {
        val screenshot = screenShotUtil.getCurrentScreenShot() ?: return null

        return BlurBuilder.blur(context, screenshot, 15f)
    }
}