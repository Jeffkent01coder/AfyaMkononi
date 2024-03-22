package com.example.afyamkononi.doctors.chat

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.databinding.ActivityDoctorChatBinding
import com.example.afyamkononi.shared.chatMongo.adapter.MessageAdapter
import com.example.afyamkononi.shared.chatMongo.api.ApiService
import com.example.afyamkononi.shared.chatMongo.api.ConversationIdResponse
import com.example.afyamkononi.shared.chatMongo.client.RetrofitClient
import com.example.afyamkononi.shared.chatMongo.model.Message
import com.example.afyamkononi.shared.chatMongo.model.MessageSend
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorChat : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorChatBinding

    private lateinit var apiService: ApiService
    private lateinit var adapter: MessageAdapter
    private lateinit var auth: FirebaseAuth

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorChatBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            intent
            name.text = intent.getStringExtra("name")
        }

        id = intent.getStringExtra("id")


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
                val message = MessageSend(auth.currentUser!!.uid, id, messageText)
                // Send the message
                sendMessage(message)
                // re-fetch messages
                getMessagesForConversation(id, auth.currentUser!!.uid)

                // Clear EditText after sending message
                binding.editTextMessage.text.clear()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }

        getMessagesForConversation(id, auth.currentUser!!.uid)

    }

    private fun sendMessage(message: MessageSend) {
        apiService.sendMessage(message).enqueue(object : Callback<ConversationIdResponse> {
            override fun onResponse(
                call: Call<ConversationIdResponse>,
                response: Response<ConversationIdResponse>
            ) {
                if (response.isSuccessful) {
                    val conversationId = response.body()?.conversationId
                    Toast.makeText(
                        this@DoctorChat,
                        "Message sent successfully, Conversation ID: $conversationId",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DoctorChat,
                        "Failed to send message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ConversationIdResponse>, t: Throwable) {
                Toast.makeText(
                    this@DoctorChat,
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
        Toast.makeText(this@DoctorChat, message, Toast.LENGTH_SHORT).show()
    }

    private fun getMessagesForConversation(sender: String?, receiver: String) {
        apiService.getMessages(sender, receiver).enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    val messages = response.body()
                    if (messages != null) {
                        adapter.updateData(messages)
                    }
                } else {
                    Toast.makeText(this@DoctorChat, "Failed to fetch messages", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(
                    this@DoctorChat,
                    "Failed to fetch messages: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}