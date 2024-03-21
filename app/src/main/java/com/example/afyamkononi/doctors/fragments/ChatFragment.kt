package com.example.afyamkononi.doctors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.FragmentChatBinding
import com.example.afyamkononi.doctors.chat.adapter.DoctorChatAdapter
import com.example.afyamkononi.shared.model.UserData

class ChatFragment : Fragment(), DoctorChatAdapter.OnChatClickListener {

    private lateinit var binding: FragmentChatBinding

    private lateinit var adapter: DoctorChatAdapter
    private lateinit var recyclerView: RecyclerView

    private var doctorChatList = mutableListOf<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.doctorChatListRecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DoctorChatAdapter(doctorChatList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun dataInitialize() {
        doctorChatList = arrayListOf(
            UserData("1", "John Doe", "john@example.com", "1234567890"),
            UserData("2", "Jane Smith", "jane@example.com", "0987654321"),
            UserData("3", "Alice Johnson", "alice@example.com", "1112223333")
        )
    }


    override fun onChatClick(chat: UserData, position: Int) {
        Toast.makeText(requireActivity(), "hey clicked", Toast.LENGTH_LONG).show()
    }

}