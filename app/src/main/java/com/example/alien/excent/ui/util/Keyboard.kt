package com.example.alien.excent.ui.util

import timber.log.Timber
import android.os.IBinder
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*


class Keyboard {

    companion object {
        fun close(activity: Activity, view: View) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm != null) {
                var token: IBinder? = null
                try {
                    token = Objects.requireNonNull(view).windowToken
                } catch (e: NullPointerException) {
                    Timber.w(e)
                }

                imm.hideSoftInputFromWindow(token, 0)
            }
        }
    }
}