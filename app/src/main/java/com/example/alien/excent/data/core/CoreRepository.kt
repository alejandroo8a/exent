package com.example.alien.excent.data.core

import com.example.alien.excent.data.core.home.EventsData
import com.example.alien.excent.network.core.CoreClient
import io.reactivex.Single
import javax.inject.Inject


class CoreRepository @Inject
internal constructor(
    private val coreClient: CoreClient
) {

    fun getEvents(idUser: Int, idLocation: Int, idCategory: Int) : Single<List<EventsData>> {
        return coreClient.getEvents(idUser, idLocation, idCategory)
    }
}