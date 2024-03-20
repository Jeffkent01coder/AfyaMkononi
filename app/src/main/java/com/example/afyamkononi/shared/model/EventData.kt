package com.example.afyamkononi.shared.model

data class EventData(
    val id: String = "",
    var uid: String? = "",
    val eventName : String? = "",
    val eventReason : String? = "",
    val eventLocation : String? = "",
    val eventTime : String? = "",
    val eventDate : String? = "",
)
