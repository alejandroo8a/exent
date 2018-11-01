package com.example.alien.excent.ui.network

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit


class LoadingDialogFragment : DialogFragment() {

    val MINIMUM_DISPLAY_TIME: Long = 700 // in milliseconds

    var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationComponentHolder.getInstance().getComponent().inject(this)
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        initTimer()
    }

    private fun initTimer() {
        startTime = System.currentTimeMillis()
    }

    override fun dismiss() {
        if(isResumed) {
            super.dismiss()
        }
    }

    override fun dismissAllowingStateLoss() {
        if(isResumed) {
            super.dismissAllowingStateLoss()
        }
    }

    fun <E> loadFinishedObservable(value: E): Observable<E> {
        val timeRemaining = getTimeRemaining()

        return Observable.just(value)
            .delay(timeRemaining, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { dismiss() }
    }

    fun <E> loadFinishedSingle(value: E): Single<E> {
        return loadFinishedObservable(value).firstOrError()
    }

    private fun getTimeRemaining(): Long {
        val timeRemaining = MINIMUM_DISPLAY_TIME - (System.currentTimeMillis() - startTime)
        return Math.max(timeRemaining, 0)
    }
}
