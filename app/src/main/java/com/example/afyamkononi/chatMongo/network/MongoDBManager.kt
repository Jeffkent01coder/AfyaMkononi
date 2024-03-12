package com.example.afyamkononi.chatMongo.network

import com.example.afyamkononi.chatMongo.model.ChatMessage
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document


class MongoDBManager {
//mongodb+srv://geoffreyerastus956:V0YNMcjo0BIqpiua@chatapp.e8188zu.mongodb.net/chatApp?" +
//            "retryWrites=true&w=majority&appName=chatApp\n
    val uri = ""

    private val mongoClient = MongoClients.create(uri)
    private val database: MongoDatabase = mongoClient.getDatabase("chatApp")
    private val collection: MongoCollection<Document> = database.getCollection("messages")
    val chatList = mutableListOf<ChatMessage>()

    init {
        fetchChatMessages()
    }

    private fun fetchChatMessages() {
        val cursor = collection.find().iterator()
        while (cursor.hasNext()) {
            val doc = cursor.next()
            val sender = doc.getString("sender")
            val message = doc.getString("message")
            chatList.add(ChatMessage(sender, message))
        }
    }

    fun sendMessage(sender: String, message: String) {
        val doc = Document("sender", sender)
            .append("message", message)
        collection.insertOne(doc)
        chatList.add(ChatMessage(sender, message))
    }

    fun closeClient() {
        mongoClient.close()
    }
}