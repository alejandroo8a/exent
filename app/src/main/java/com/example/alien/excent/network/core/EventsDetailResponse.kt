package com.example.alien.excent.network.core

data class EventsDetailResponse(
    val idEvent: Int,
    val name: String,
    val date: String,
    val time: String,
    val place: String,
    val image: String,
    val price: Float
)