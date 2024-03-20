package com.example.afyamkononi.shared.chatMongo.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("_id")
    val id: String,
    val sender: String,
    val receiver: String,
    val message: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)


