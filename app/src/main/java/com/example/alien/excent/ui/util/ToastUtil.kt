package com.example.alien.excent.ui.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.HttpException

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ToastUtil @Inject constructor() {

    companion object {

        private val DEFAULT_DURATION_TOAST = Toast.LENGTH_SHORT
    }

    internal lateinit var currentToast: Toast

    fun showToast(context: Context, error: HttpException) {
        showToast(context, error.message!!)
    }

    fun showToast(context: Context, error: HttpException, duration: Int) {
        showToast(context, error.message!!, duration)
    }

    fun showToast(context: Context, stringResourceId: Int) {
        showToast(context, stringResourceId, DEFAULT_DURATION_TOAST)
    }

    fun showToast(context: Context, message: String) {
        showToast(context, message, DEFAULT_DURATION_TOAST)
    }

    /**
     * Helpers
     */

    private fun showToast(context: Context?, stringResourceId: Int, duration: Int) {
        if (context == null) {
            Log.e("TOAST_UTIL", "showToast(): context is null. No message shown")
            return
        }

        currentToast = Toast.makeText(context, stringResourceId, duration)
        currentToast.show()
    }

    private fun showToast(context: Context?, message: String, duration: Int) {
        if (context == null) {
            Log.e("TOAST_UTIL", "showToast(): context is null. No message shown")
            return
        }

        currentToast = Toast.makeText(context, message, duration)
        currentToast.show()
    }

}
