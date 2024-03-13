package com.example.afyamkononi.chatMongo.model

data class MessageSend(
    val sender: String? = null,
    val receiver: String? = null,
    val message: String? = null
)
