package com.example.afyamkononi.chatMongo.api

import com.example.afyamkononi.chatMongo.model.Message
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/messages")
    fun sendMessage(@Body message: Message): Call<ConversationIdResponse> // Modify return type

    @GET("/messages/{id}")
    fun getMessagesForConversation(@Path("id") id: String): Call<List<Message>>
}

data class ConversationIdResponse(
    @SerializedName("conversationId")
    val conversationId: String
)
