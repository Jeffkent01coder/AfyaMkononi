package com.example.afyamkononi.doctors.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.databinding.FragmentChatBinding
import com.example.afyamkononi.doctors.chat.DocChat
import com.example.afyamkononi.doctors.chat.adapter.DoctorChatAdapter
import com.example.afyamkononi.shared.fire.screens.MyChats
import com.example.afyamkononi.shared.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber

class ChatFragment : Fragment(), DoctorChatAdapter.OnChatClickListener {

    private lateinit var binding: FragmentChatBinding

//    private lateinit var adapter: DoctorChatAdapter
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var apiService: ApiService
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: FirebaseDatabase
//
//    private var doctorChatList = mutableListOf<DocChat>()

    private lateinit var adapter: DoctorChatAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var doctorChatList = mutableListOf<DocChat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.users.setOnClickListener {
//            val intent = Intent(requireActivity(), Myusers::class.java)
//            startActivity(intent)
//        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("messages")

        fetchConversations(auth.currentUser!!.uid)

        val layoutManager = LinearLayoutManager(context)
        binding.doctorChatsListRecyclerView.layoutManager = layoutManager

        adapter = DoctorChatAdapter(doctorChatList, this)
        binding.doctorChatsListRecyclerView.adapter = adapter


//        apiService = RetrofitClient.createService(ApiService::class.java)
//
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//
//
//        fetchConversations(auth.currentUser!!.uid)


//        dataInitialize()
//        val layoutManager = LinearLayoutManager(context)
//        recyclerView = binding.doctorChatsListRecyclerView
//        recyclerView.layoutManager = layoutManager
//        recyclerView.setHasFixedSize(true)
//        adapter = DoctorChatAdapter(doctorChatList, this)
//        recyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()

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

                    Timber.tag("result")
                        .d(
                            "%s%s",
                            "Sender id: " + senderId.toString() + ", Receiver id: " + receiverId + ", Message is: ",
                            message
                        )

                    if (senderId == currentUserUid || receiverId == currentUserUid) {
                        // Determine the other user's ID
                        val otherUserId = if (senderId == currentUserUid) receiverId else senderId
                        Timber.tag("otherUserId").d(otherUserId.toString())
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
        val intent = Intent(requireActivity(), MyChats::class.java).apply {
            putExtra("uid", chat.uid)
            putExtra("username", chat.username)
        }
        startActivity(intent)
    }


//    private fun getUserNameById(userId: String, callback: (String?) -> Unit) {
//        val userRef = database.reference.child("registeredUser/").child(userId)
//
//        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                Timber.tag("name").d("The snapshot is: $dataSnapshot")
//                val user = dataSnapshot.getValue(UserData::class.java)
//                Timber.tag("name").d("The user is: $user")
//                val userName = user?.name
//                callback(userName)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle error
//                callback(null)
//            }
//        })
//    }
//
//    private fun fetchConversations(id: String) {
//        apiService.getConversations(id).enqueue(object : Callback<List<Chat>> {
//            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
//                if (response.isSuccessful) {
//                    val chats: List<Chat>? = response.body()
//                    if (chats != null) {
//                        for(chat in chats) {
//                            getUserNameById(chat.otherMember) { name ->
//                                if (name != null) {
//                                    Timber.tag("name").d("The name is: $name")
//                                    val chat = DocChat(chat.otherMember, name)
//                                    doctorChatList.add(chat)
//                                    Timber.tag("chats").d("Doctor chats are: $doctorChatList")
//
//                                    // Notify adapter of dataset changes
//                                    adapter.notifyDataSetChanged()
//                                } else {
//                                    Timber.tag("name").d("Name is null")
//                                }
//                            }
//                        }
//                    } else {
//                        Timber.tag("messages").d("Messages null")
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Failed to fetch messages", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
//                Toast.makeText(
//                    requireContext(),
//                    "Failed to fetch messages: ${t.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }
//
//
//    override fun onChatClick(chat: DocChat, position: Int) {
//        val intent = Intent(requireActivity(), DoctorChat::class.java)
//        intent.putExtra("id", chat.uid)
//        intent.putExtra("name", chat.username)
//        startActivity(intent)
//    }


}
