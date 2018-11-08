package com.example.alien.excent.ui.util.overlay

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ScreenShotUtil @Inject
internal constructor(){

    private lateinit var currentScreenShot: Bitmap

    fun takeScreenShot(activity: Activity): Bitmap {
        val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
        val bitmap: Bitmap
        val rawScreenshot = takeScreenShot(rootView)

        val firstChild = (rootView as ViewGroup).getChildAt(0)
        if (firstChild is CoordinatorLayout) {
            // If the first child is a CoordinatorLayout, the view at android.R.id.content starts at 0,0, which means
            // it will include blank space for the status bar, and we'll have to remove that space from the screenshot.
            val statusBarHeight = getStatusBarHeight(activity)
            bitmap = Bitmap.createBitmap(
                rawScreenshot,
                0,
                statusBarHeight,
                rawScreenshot.width,
                rawScreenshot.height - statusBarHeight
            )
        } else {
            // If it's not a CoordinatorLayout, the view at android.R.id.content starts at 0,{statusBarHeight}, so we
            // don't need to remove any of the screenshot.
            bitmap = rawScreenshot
        }
        currentScreenShot = bitmap
        return bitmap
    }

    fun takeScreenShot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.getWidth(),
            view.getHeight(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

    fun getCurrentScreenShot(): Bitmap {
        return currentScreenShot
    }

}