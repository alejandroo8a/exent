package com.example.alien.excent.ui.util

import android.content.Context
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.annotation.NonNull
import android.view.View
import timber.log.Timber
import android.content.ActivityNotFoundException
import android.support.annotation.StringRes
import com.example.alien.excent.R

class ThirdPartyIntentLauncher @Inject
internal constructor(
    private val applicationContext: Context,
    private val snackbarUtil: SnackbarUtil
) {

    fun launchMap(address: String, @NonNull viewForErrorSnackbar: View) {
        if (address.isEmpty()) {
            snackbarUtil.showSnackbar(viewForErrorSnackbar, R.string.map_failed_invalid_location)
        } else {
            Timber.d("Launching map for address: %s", address)
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse("geo:0,0?q=$address")

            launch(mapIntent, R.string.map_failed_no_map_app, viewForErrorSnackbar)
        }
    }

    private fun launch(intent: Intent, @StringRes activityNotFoundMessage: Int, viewForErrorSnackbar: View) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            applicationContext.startActivity(intent)
        } catch (error: ActivityNotFoundException) {
            snackbarUtil.showSnackbar(viewForErrorSnackbar, activityNotFoundMessage)
        }
    }

}