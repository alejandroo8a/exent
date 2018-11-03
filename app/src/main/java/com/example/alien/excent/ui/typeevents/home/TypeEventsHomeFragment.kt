package com.example.alien.excent.ui.typeevents.home

import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.navigation.Navigation
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_type_events_home)
class TypeEventsHomeFragment : ViewModelFragment<TypeEventsHomeViewModel>() {

    @Callback
    lateinit var navigation: Navigation

    override fun viewModelClass() = TypeEventsHomeViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)
}
