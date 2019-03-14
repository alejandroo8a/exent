package com.example.alien.excent.network.event

import javax.inject.Inject

class EventNetworkMapper @Inject
internal constructor() {

    fun toEventSeatsRequest(idEvent: Int, seats: ArrayList<String>) = EventSeatsRequest(idEvent, seats)

}