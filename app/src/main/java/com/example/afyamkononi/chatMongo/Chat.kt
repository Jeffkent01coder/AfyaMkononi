package com.example.afyamkononi.chatMongo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.chatMongo.adapter.MessageAdapter
import com.example.afyamkononi.chatMongo.api.ApiService
import com.example.afyamkononi.chatMongo.api.ConversationIdResponse
import com.example.afyamkononi.chatMongo.client.RetrofitClient
import com.example.afyamkononi.chatMongo.model.Message
import com.example.afyamkononi.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Chat : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var apiService: ApiService
    private lateinit var adapter: MessageAdapter
    private lateinit var auth : FirebaseAuth

    private var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            intent
            doctorName.text = intent.getStringExtra("doctorName")
            doctorProfession.text = intent.getStringExtra("doctorProfession")
            id = intent.getStringExtra("id")
        }

        // Initialize RecyclerView and adapter
        adapter = MessageAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Initialize ApiService
        apiService = RetrofitClient.createService(ApiService::class.java)

        // Set click listener for the "Send" button
        binding.btnSend.setOnClickListener {
            // Retrieve message from EditText
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                // Create a Message object with sender as current user ID and receiver ID obtained from intent
                val message = Message(auth.currentUser!!.uid, id, messageText)
                // Send the message
                sendMessage(message)
                // Clear EditText after sending message
                binding.editTextMessage.text.clear()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }

        // Check if there is a conversation ID saved in preferences
        val sharedPreferences = getSharedPreferences("conversation_data", Context.MODE_PRIVATE)
        val conversationId = sharedPreferences.getString("conversation_id", null)
        if (conversationId != null) {
            // If conversation ID exists, make API call to get messages
            getMessagesForConversation(conversationId)
        }
    }


    private fun sendMessage(message: Message) {
        apiService.sendMessage(message).enqueue(object : Callback<ConversationIdResponse> {
            override fun onResponse(
                call: Call<ConversationIdResponse>,
                response: Response<ConversationIdResponse>
            ) {
                if (response.isSuccessful) {
                    val conversationId = response.body()?.conversationId
                    // Save conversation ID to SharedPreferences
                    saveConversationIdToLocalStorage(conversationId)
                    Toast.makeText(
                        this@Chat,
                        "Message sent successfully, Conversation ID: $conversationId",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Show toast for successfully saving conversation ID
                    showToast("Conversation ID saved successfully")
                } else {
                    Toast.makeText(
                        this@Chat,
                        "Failed to send message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ConversationIdResponse>, t: Throwable) {
                Toast.makeText(
                    this@Chat,
                    "Failed to send message: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun saveConversationIdToLocalStorage(conversationId: String?) {
        val sharedPreferences = getSharedPreferences("conversation_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("conversation_id", conversationId)
        editor.apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@Chat, message, Toast.LENGTH_SHORT).show()
    }

    private fun getMessagesForConversation(conversationId: String) {
        apiService.getMessagesForConversation(conversationId).enqueue(object : Callback<List<Message>> {
            override fun onResponse(
                call: Call<List<Message>>,
                response: Response<List<Message>>
            ) {
                if (response.isSuccessful) {
                    val messages = response.body()
                    if (messages != null) {
                        adapter.updateData(messages)
                    }
                } else {
                    Toast.makeText(this@Chat, "Failed to fetch messages", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(
                    this@Chat,
                    "Failed to fetch messages: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



}