package com.example.alien.excent.ui.util

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import retrofit2.HttpException

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarUtil @Inject
internal constructor() {

    private val defaultDurationSnackbar = Snackbar.LENGTH_SHORT

    fun showSnackbar(view: View, error: HttpException) {
        showSnackbar(view, error.message!!)
    }

    fun showSnackbar(view: View, error: HttpException, duration: Int) {
        showSnackbar(view, error.message!!, duration)
    }

    fun showSnackbar(view: View, stringResourceId: Int) {
        showSnackbar(view, stringResourceId, defaultDurationSnackbar)
    }

    fun showSnackbar(view: View, message: String) {
        showSnackbar(view, message, defaultDurationSnackbar)
    }

    private fun showSnackbar(view: View?, stringResourceId: Int, duration: Int) {
        if (view == null) {
            Log.e("SNACKBAR", "showSnackbar(): view is null. No message shown")
            return
        }

        Snackbar.make(view, stringResourceId, duration).show()
    }

    private fun showSnackbar(view: View?, message: String, duration: Int) {
        if (view == null) {
            Log.e("SNACKBAR", "showSnackbar(): view is null. No message shown")
            return
        }

        Snackbar.make(view, message, duration).show()
    }
}
