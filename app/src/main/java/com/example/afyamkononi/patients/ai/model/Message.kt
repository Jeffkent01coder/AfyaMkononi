package com.example.afyamkononi.patients.ai.model

data class Message(val text: String, val type: MessageType)

enum class MessageType {
    SENDER,
    RESPONSE
}
