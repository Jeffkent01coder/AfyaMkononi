package com.example.afyamkononi.shared.fire.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityMyChatsBinding
import com.example.afyamkononi.shared.fire.adapter.ChatAdapter
import com.example.afyamkononi.shared.fire.model.Message
import com.example.afyamkononi.shared.fire.screens.userside.MyDoctors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyChats : AppCompatActivity() {
    private lateinit var binding: ActivityMyChatsBinding

    private lateinit var adapter: ChatAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val messagesList = mutableListOf<Message>()

    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyChatsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            intent
            userName.text = intent.getStringExtra("name")
        }

        uid = intent.getStringExtra("id")

        if (auth.currentUser == null) {
            startActivity(Intent(this, MyDoctors::class.java))
            finish()
            return
        }

        database = FirebaseDatabase.getInstance().reference.child("messages")

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        adapter = ChatAdapter(messagesList, auth.currentUser!!.uid)
        binding.recyclerView.adapter = adapter

        binding.sendButton.setOnClickListener {
            val messageText = binding.editText.text.toString()
            val receiverId = uid ?: "" // Fetch receiver ID from intent or wherever you obtain it
            sendMessage(messageText, receiverId)
        }

        listenForMessages()
    }

    private fun sendMessage(messageText: String, receiverId: String) {
        val senderId = auth.currentUser!!.uid
        val message = Message(messageText, senderId, receiverId)
        database.push().setValue(message)
        binding.editText.text.clear()
    }

    private fun listenForMessages() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        messagesList.add(message)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyChats, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
