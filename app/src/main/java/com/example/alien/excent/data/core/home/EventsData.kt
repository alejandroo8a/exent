package com.example.alien.excent.data.core.home

import com.example.alien.excent.data.NetworkResult

data class EventsData(
    var idEvent: Int = 0,
    var description: String = "",
    var name: String = "",
    var date: String = "",
    var time: String = "",
    var place: String = "",
    var image: String = "",
    var price: Float = 0.0f,
    var result: NetworkResult = NetworkResult.SUCCESS
) {
    constructor(result: NetworkResult) : this() {
        this.result = result
    }
}