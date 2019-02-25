package com.example.alien.excent.ui.core.home

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.data.ResultData
import com.example.alien.excent.data.core.home.EventsData
import javax.inject.Inject

class UiHomeMapper @Inject
internal constructor() {

    fun toUiEvent(eventData : ResultData<List<EventsData>>) : ResultData<List<UiEvents>> {
        if(eventData.networkResult == NetworkResult.SUCCESS) {
            val uiEvents = ArrayList<UiEvents>()
            for(event in eventData.data!!) {
                uiEvents.add(UiEvents(event.idEvent, event.description, event.name, event.date, event.time, event.place, event.price.toString(), event.image))
            }
            return ResultData(uiEvents)
        }
        return ResultData(eventData.networkResult)
    }
}