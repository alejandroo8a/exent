package com.example.alien.excent.ui.core.home

import com.example.alien.excent.data.core.home.EventsData
import javax.inject.Inject

class UiHomeMapper @Inject
internal constructor() {

    fun toUiEvent(eventData : List<EventsData>) : List<UiEvents> {
        val uiEvents = ArrayList<UiEvents>()
        for(event in eventData) {
            uiEvents.add(UiEvents(event.idEvent, event.name, event.date, event.time, event.place, event.price.toString(), event.image))
        }
        return  uiEvents
    }
}