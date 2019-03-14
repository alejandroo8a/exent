package com.example.alien.excent.network.event

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.ResponseConversions
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventClient @Inject
internal constructor(
    private val api: NetworkApi,
    private val networkMapper: EventNetworkMapper
) {

    fun sendSeats(idEvent: Int, seats: ArrayList<String>): Single<NetworkResult> {
        return api.submitSeats(networkMapper.toEventSeatsRequest(idEvent, seats))
            .delay(2L, TimeUnit.SECONDS)
            .toSingleDefault(NetworkResult.SUCCESS)
            .onErrorReturn{ ResponseConversions().toNetworkResult(it) }
    }
}