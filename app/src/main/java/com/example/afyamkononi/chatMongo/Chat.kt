package com.example.afyamkononi.chatMongo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.chatMongo.adapter.ChatAdapter
import com.example.afyamkononi.chatMongo.network.MongoDBManager
import com.example.afyamkononi.databinding.ActivityChatBinding

class Chat : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding


    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mongoDBManager: MongoDBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mongoDBManager = MongoDBManager()

        chatAdapter = ChatAdapter(mongoDBManager.chatList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Chat)
            adapter = chatAdapter
        }

        binding.btnSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotBlank()) {
                mongoDBManager.sendMessage("user1", message)
                binding.editTextMessage.text.clear()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mongoDBManager.closeClient()
    }


}