package com.example.afyamkononi.shared.fire.screens.doctorside

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.databinding.ActivityMyusersBinding
import com.example.afyamkononi.doctors.chat.DocChat
import com.example.afyamkononi.doctors.chat.adapter.DoctorChatAdapter
import com.example.afyamkononi.shared.chatMongo.model.Chat
import com.example.afyamkononi.shared.fire.model.Message
import com.example.afyamkononi.shared.fire.screens.MyChats
import com.example.afyamkononi.shared.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Myusers : AppCompatActivity(), DoctorChatAdapter.OnChatClickListener {

    private lateinit var binding: ActivityMyusersBinding
    private lateinit var adapter: DoctorChatAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var doctorChatList = mutableListOf<DocChat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyusersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("messages")

        fetchConversations(auth.currentUser!!.uid)

        val layoutManager = LinearLayoutManager(this)
        binding.doctorChatListRecyclerView.layoutManager = layoutManager

        adapter = DoctorChatAdapter(doctorChatList, this)
        binding.doctorChatListRecyclerView.adapter = adapter

    }

    private fun getUserNameById(userId: String?, callback: (String?) -> Unit) {
        val userRef = FirebaseDatabase.getInstance().getReference("registeredUser/$userId")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Timber.tag("name").d("The snapshot is: $dataSnapshot")
                val user = dataSnapshot.getValue(UserData::class.java)
                Timber.tag("name").d("The user is: $user")
                val userName = user?.name
                callback(userName)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }


    private fun fetchConversations(currentUserUid: String) {
        val database = FirebaseDatabase.getInstance()
        val messagesRef = database.getReference("messages")

        messagesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val senderId = snapshot.child("senderId").getValue(String::class.java)
                    val receiverId = snapshot.child("receiverId").getValue(String::class.java)
                    val message = snapshot.child("messageText").getValue(String::class.java)

                    Log.d("result", "Sender id: ${senderId.toString()}, Receiver id: ${receiverId}, Message is: ${message}")

                    if (senderId == currentUserUid || receiverId == currentUserUid) {
                        // Determine the other user's ID
                        val otherUserId = if (senderId == currentUserUid) receiverId else senderId
                        Log.d("otherUserId", otherUserId.toString())
                        // Get the other user's name
                        getUserNameById(otherUserId.toString()) { otherUserName ->
                            // Process the message and other user's details here
                            val docChat = DocChat(otherUserId.toString(), otherUserName.toString())
                            doctorChatList.add(docChat)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Fetch conversations cancelled: ${databaseError.toException()}")
            }
        })
    }



    override fun onChatClick(chat: DocChat, position: Int) {
        val intent = Intent(this, MyChats::class.java).apply {
            putExtra("uid", chat.uid)
            putExtra("username", chat.username)
        }
        startActivity(intent)
    }
}
