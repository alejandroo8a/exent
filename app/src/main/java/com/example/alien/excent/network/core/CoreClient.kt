package com.example.alien.excent.network.core

import com.example.alien.excent.data.ResultData
import com.example.alien.excent.data.core.home.EventsData
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.core.home.HomeNetworkMapper
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CoreClient @Inject
internal constructor(
    private val api: NetworkApi,
    private val networkMapper: HomeNetworkMapper
) {

    fun getEvents(idLocation: Int, idCategory: Int) : Single<ResultData<List<EventsData>>> {
        return api.getEvents(idLocation, idCategory)
            .delay(1L, TimeUnit.SECONDS)
            .map(networkMapper::toEventsData)
            .onErrorReturn(networkMapper::toEventsData)
    }

    fun searchEvents(idLocation: Int, event: String) : Single<ResultData<List<EventsData>>> {
        return api.searchEvents(idLocation, event)
            .delay(1L, TimeUnit.SECONDS)
            .map(networkMapper::toEventsData)
            .onErrorReturn(networkMapper::toEventsData)
    }
}