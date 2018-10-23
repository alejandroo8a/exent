package com.example.alien.excent.ui.base

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.metova.slim.SlimFragment

abstract class BaseFragment: SlimFragment() {

    private lateinit var unbinder: Unbinder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}