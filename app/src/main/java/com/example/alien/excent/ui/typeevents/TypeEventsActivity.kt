package com.example.alien.excent.ui.typeevents

import android.os.Bundle
import com.example.alien.excent.R
import com.example.alien.excent.module.ApplicationComponentHolder
import com.example.alien.excent.ui.base.ViewModelActivity
import com.example.alien.excent.ui.navigation.UiAction
import com.metova.slim.annotation.Layout

@Layout(R.layout.activity_type_events)
class TypeEventsActivity : ViewModelActivity<TypeEventsViewModel>() {

    override fun viewModelClass() = TypeEventsViewModel::class.java

    override fun inject() = ApplicationComponentHolder.INSTANCE.getComponent().inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToAction(UiAction.TYPE_EVENT_HOME)
    }
}
