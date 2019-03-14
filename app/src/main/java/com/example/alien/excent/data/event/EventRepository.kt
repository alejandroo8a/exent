package com.example.alien.excent.data.event

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.event.EventClient
import io.reactivex.Single
import javax.inject.Inject

class EventRepository @Inject
internal constructor(
    private val eventClient: EventClient
) {

    fun sendSeats(idEvent: Int, seats: ArrayList<String>): Single<NetworkResult> = eventClient.sendSeats(idEvent, seats)
}