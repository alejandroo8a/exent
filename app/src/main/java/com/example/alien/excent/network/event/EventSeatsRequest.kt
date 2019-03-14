package com.example.alien.excent.network.event

data class EventSeatsRequest (
    val idEvent: Int,
    val seats: ArrayList<String>
)