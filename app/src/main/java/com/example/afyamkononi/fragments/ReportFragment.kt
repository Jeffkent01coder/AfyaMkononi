package com.example.afyamkononi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.adapters.EventsAdapter
import com.example.afyamkononi.databinding.FragmentReportBinding
import com.example.afyamkononi.model.EventData
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ReportFragment : Fragment(), EventsAdapter.OnEventClickListener {
    private lateinit var binding: FragmentReportBinding
    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventArrayList = mutableListOf<EventData>()

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEvents()

        initializeRecyclerView()
        adapter.notifyDataSetChanged()
    }

    private fun initializeRecyclerView() {
        recyclerView = binding.pastEventsRecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(eventArrayList, this)
        recyclerView.adapter = adapter
    }


    override fun onEventClick(event: EventData, position: Int) {

        Toast.makeText(requireActivity(), "Event clicked", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getEvents() {
        database = Firebase.database.reference
        database.child("UpcomingEvents").get()
            .addOnSuccessListener { dataSnapshot ->
                for (jobSnapshot in dataSnapshot.children) {
                    val id = jobSnapshot.child("id").getValue(String::class.java)
                    val personMeet = jobSnapshot.child("personMeet").getValue(String::class.java)
                    val appointmentTitle =
                        jobSnapshot.child("appointmentTitle").getValue(String::class.java)
                    val eventLocation =
                        jobSnapshot.child("eventLocation").getValue(String::class.java)
                    val tvTime = jobSnapshot.child("tvTime").getValue(String::class.java)
                    val tvSelectDate =
                        jobSnapshot.child("tvSelectDate").getValue(String::class.java)
                    val uid = jobSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && personMeet != null && appointmentTitle != null && eventLocation != null && tvTime != null && tvSelectDate != null && uid != null) {
                        val event = EventData(
                            id,
                            personMeet,
                            appointmentTitle,
                            eventLocation,
                            tvTime,
                            tvSelectDate
                        )
                        eventArrayList.add(event)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }


}