package com.example.alien.excent.network.core

import com.example.alien.excent.data.core.home.EventsData
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.core.home.HomeNetworkMapper
import io.reactivex.Single
import javax.inject.Inject

class CoreClient @Inject
internal constructor(
    private val api: NetworkApi,
    private val networkMapper: HomeNetworkMapper
) {

    fun getEvents(idUser: Int, idLocation: Int, idCategory: Int) : Single<List<EventsData>> {
        return api.getEvents(1, idLocation, idCategory)
            .map(networkMapper::toEventsData)
            .onErrorReturn(networkMapper::toEventsData)
    }
}