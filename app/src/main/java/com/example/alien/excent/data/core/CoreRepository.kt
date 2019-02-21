package com.example.alien.excent.data.core

import com.example.alien.excent.data.ResultData
import com.example.alien.excent.data.core.home.EventsData
import com.example.alien.excent.network.core.CoreClient
import io.reactivex.Single
import javax.inject.Inject

class CoreRepository @Inject
internal constructor(
    private val coreClient: CoreClient
) {

    fun getEvents(idLocation: Int, idCategory: Int): Single<ResultData<List<EventsData>>> {
        return coreClient.getEvents(idLocation, idCategory)
    }

    fun searchEvents(idLocation: Int, event: String): Single<ResultData<List<EventsData>>> {
        return coreClient.searchEvents(idLocation, event)
    }
}