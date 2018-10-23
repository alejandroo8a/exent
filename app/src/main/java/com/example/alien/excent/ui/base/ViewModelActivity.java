package com.example.alien.excent.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;

/**
 * Base activity class for Activities that will be managed by a {@link ViewModel}.
 * Manages subscriptions and disposes of them in {@code onStop()}. Subclasses should typically
 * add subscriptions in {@code subscribeOnStart()}.
 */
public abstract class ViewModelActivity<VM extends ViewModel> extends NavigationActivity {

    @Inject GenericViewModelFactory<VM> viewModelFactory;

    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass());
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeOnStart();
    }

    @Override
    protected void onStop() {
        subscriptions.clear();
        super.onStop();
    }

    @NonNull
    protected abstract Class<VM> viewModelClass();

    protected abstract void inject();

    /**
     * Sub-classes should subscribe to any ViewModel methods that are necessary for the full duration of the screen
     * in this method
     */
    protected void subscribeOnStart() {
        // Optional hook method
    }

    protected final VM viewModel() {
        return viewModel;
    }

    /**
     * All Rx subscriptions should be registered here so they are properly disposed of when the Activity is stopped
     */
    protected final void addSubscription(Disposable subscription) {
        subscriptions.add(subscription);
    }
}