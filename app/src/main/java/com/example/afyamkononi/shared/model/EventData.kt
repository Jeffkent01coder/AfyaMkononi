package com.example.afyamkononi.shared.model

data class EventData(
    val id: String = "",
    var uid: String? = "",
    val personMeet : String? = "",
    val appointmentTitle : String? = "",
    val eventLocation : String? = "",
    val tvTime : String? = "",
    val tvSelectDate : String? = "",
    val doctorId : String = ""
)
