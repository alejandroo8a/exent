package com.example.alien.excent.ui.base

import io.reactivex.disposables.Disposable
import android.text.method.TextKeyListener.clear
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import android.arch.lifecycle.ViewModel
import android.support.annotation.NonNull
import android.support.annotation.Nullable


abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: GenericViewModelFactory<VM>

    private val subscriptions = CompositeDisposable()

    private var viewModel: VM? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass())
    }

    override fun onStart() {
        super.onStart()
        subscribeOnStart()
    }

    override fun onStop() {
        subscriptions.clear()
        super.onStop()
    }

    @NonNull
    protected abstract fun viewModelClass(): Class<VM>

    protected abstract fun inject()

    /**
     * Sub-classes should subscribe to any ViewModel methods that are necessary for the full duration of the screen
     * in this method
     */
    protected open fun subscribeOnStart() {
        // Optional hook method
    }

    protected fun viewModel(): VM? {
        return viewModel
    }

    /**
     * All Rx subscriptions should be registered here so they are properly disposed of when the Fragment is stopped
     */
    protected fun addSubscription(subscription: Disposable) {
        subscriptions.add(subscription)
    }
}