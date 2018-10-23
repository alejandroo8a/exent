package com.example.alien.excent.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Manages lazy injection for {@link ViewModel}s.
 * Used in {@link android.arch.lifecycle.ViewModelProviders#of(Fragment, ViewModelProvider.Factory)}
 * and {@link android.arch.lifecycle.ViewModelProviders#of(FragmentActivity, ViewModelProvider.Factory)}
 */
public class GenericViewModelFactory<VM extends ViewModel> implements ViewModelProvider.Factory {

    private final Lazy<VM> lazyViewModel;

    @Inject
    public GenericViewModelFactory(Lazy<VM> viewModel) {
        this.lazyViewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final VM viewModel = lazyViewModel.get();

        // Method can’t actually be called with the wrong class type in practice, so suppress the warning:
        @SuppressWarnings("unchecked") final T castViewModel = (T) viewModel;
        return castViewModel;
    }
}
