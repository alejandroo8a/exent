package com.example.alien.excent.network.core.home

import com.example.alien.excent.data.core.home.EventsData
import com.example.alien.excent.network.ResponseConversions
import com.example.alien.excent.network.core.EventsResponse
import javax.inject.Inject

class HomeNetworkMapper @Inject
internal constructor(){

    fun toEventsData(detailResponse: EventsResponse) : List<EventsData> {
        val eventsData = ArrayList<EventsData>()
        for(event in detailResponse.events) {
            eventsData.add(EventsData(event.idEvent, event.name, event.date, event.time, event.place, event.image, event.price))
        }
        return eventsData
    }

    fun toEventsData(throwable: Throwable) : List<EventsData> {
        val eventsData = ArrayList<EventsData>()
        eventsData.add(EventsData(ResponseConversions().toNetworkResult(throwable)))
        return eventsData
    }
}