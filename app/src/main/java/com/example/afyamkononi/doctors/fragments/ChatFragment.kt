package com.example.afyamkononi.doctors.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.FragmentChatBinding
import com.example.afyamkononi.doctors.chat.DocChat
import com.example.afyamkononi.doctors.chat.DoctorChat
import com.example.afyamkononi.doctors.chat.adapter.DoctorChatAdapter
import com.example.afyamkononi.shared.chatMongo.api.ApiService
import com.example.afyamkononi.shared.chatMongo.client.RetrofitClient
import com.example.afyamkononi.shared.chatMongo.model.Chat
import com.example.afyamkononi.shared.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ChatFragment : Fragment(), DoctorChatAdapter.OnChatClickListener {

    private lateinit var binding: FragmentChatBinding

    private lateinit var adapter: DoctorChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ApiService
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

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


        apiService = RetrofitClient.createService(ApiService::class.java)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        fetchConversations(auth.currentUser!!.uid)


//        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.doctorChatListRecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DoctorChatAdapter(doctorChatList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun getUserNameById(userId: String, callback: (String?) -> Unit) {
        val userRef = database.reference.child("registeredUser/").child(userId)

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

    private fun fetchConversations(id: String) {
        apiService.getConversations(id).enqueue(object : Callback<List<Chat>> {
            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                if (response.isSuccessful) {
                    val chats: List<Chat>? = response.body()
                    if (chats != null) {
                        for(chat in chats) {
                            getUserNameById(chat.otherMember) { name ->
                                if (name != null) {
                                    Timber.tag("name").d("The name is: $name")
                                    val chat = DocChat(chat.otherMember, name, chat.lastMessage)
                                    doctorChatList.add(chat)
                                    Timber.tag("chats").d("Doctor chats are: $doctorChatList")

                                    // Notify adapter of dataset changes
                                    adapter.notifyDataSetChanged()
                                } else {
                                    Timber.tag("name").d("Name is null")
                                }
                            }
                        }
                    } else {
                        Timber.tag("messages").d("Messages null")
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch messages", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Failed to fetch messages: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun onChatClick(chat: DocChat, position: Int) {
        val intent = Intent(requireActivity(), DoctorChat::class.java)
        intent.putExtra("id", chat.id)
        intent.putExtra("name", chat.username)
        startActivity(intent)
    }


}
