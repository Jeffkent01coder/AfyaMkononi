package com.example.afyamkononi.chatMongo.model

data class Message(
    val sender: String,
    val receiver: String? = "",
    val message: String
)
