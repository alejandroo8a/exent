package com.example.alien.excent.ui.core.home

import java.io.Serializable

data class UiEvents(
    val idEvent: Int,
    val description: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val price: String,
    val image : String
) : Serializable