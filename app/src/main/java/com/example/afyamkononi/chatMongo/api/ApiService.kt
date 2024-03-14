package com.example.afyamkononi.chatMongo.api

import com.example.afyamkononi.chatMongo.model.Message
import com.example.afyamkononi.chatMongo.model.MessageSend
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/messages")
    fun sendMessage(
        @Body message: MessageSend
    ): Call<ConversationIdResponse>

    @GET("/messages/{sender}/{receiver}")
    fun getMessages(
        @Path("sender") sender: String?,
        @Path("receiver") receiver: String
    ): Call<List<Message>>
}

data class ConversationIdResponse(
    @SerializedName("conversationId")
    val conversationId: String
)
