package com.example.alien.excent.ui.event.core

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelFragment
import com.example.alien.excent.ui.core.home.UiEvents
import com.example.alien.excent.ui.navigation.Navigation
import com.metova.slim.annotation.Callback
import com.metova.slim.annotation.Layout

@Layout(R.layout.fragment_event_core)
class EventCoreFragment : ViewModelFragment<EventCoreViewModel>() {

    companion object {

        private const val event = "EVENT"

        fun newInstance(uiEvent : UiEvents): EventCoreFragment{
            val fragment = EventCoreFragment()
            val args = Bundle()
            args.putSerializable(event, uiEvent)
            fragment.arguments = args
            return fragment
        }
    }

    @Callback
    lateinit var  navigation: Navigation

    override fun viewModelClass() = EventCoreViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

}
