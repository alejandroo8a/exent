package com.example.alien.excent.ui.event

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.core.home.UiEvents
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout

@Layout(R.layout.activity_event)
class EventActivity : ViewModelActivity<EventViewModel>() {

    private val event = "EVENT"

    override fun viewModelClass() = EventViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToAction(UiAction.EVENT_CORE, intent.extras.getSerializable(event))
    }
}
