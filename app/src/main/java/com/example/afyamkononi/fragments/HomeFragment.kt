package com.example.afyamkononi.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.R
import com.example.afyamkononi.adapters.EventsAdapter
import com.example.afyamkononi.databinding.FragmentHomeBinding
import com.example.afyamkononi.model.EventData
import com.example.afyamkononi.screens.*
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class HomeFragment : Fragment(), EventsAdapter.OnEventClickListener {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventArrayList = mutableListOf<EventData>()


    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEvents()

        binding.bmi.setOnClickListener {
            val intent = Intent(requireActivity(), BmiCalculator::class.java)
            startActivity(intent)
        }

        binding.news.setOnClickListener {
            val intent = Intent(requireActivity(), News::class.java)
            startActivity(intent)
        }

        binding.doctors.setOnClickListener {
            val intent = Intent(requireActivity(), Doctors::class.java)
            startActivity(intent)
        }

        binding.todo.setOnClickListener {
            val intent = Intent(requireActivity(), Todo::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(requireActivity(), Profile::class.java)
            startActivity(intent)
        }

//        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(eventArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }

//    private fun dataInitialize() {
//        eventArrayList = arrayListOf(
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Mental Checkup","Weiwaei Way","12:00 - 14:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Tummy Checkup","Waiyaki Way","12:00 - 15:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Skin Checkup","Waiyaki Way","12:00 - 12:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","21:00 - 22:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "head Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Heart Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
//            EventData(R.drawable.docsimage, "Dr Sean Leaky", "Bank Checkup","Waiyaki Way","12:00 - 14:00","13/23/2023"),
//
//        )}

    override fun onEventClick(event: EventData, position: Int) {
        Toast.makeText(requireActivity(),"Event clicked", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getEvents() {
        database = Firebase.database.reference
        database.child("UpcomingEvents").get()
            .addOnSuccessListener { dataSnapshot ->
                for (jobSnapshot in dataSnapshot.children) {
                    val id = jobSnapshot.child("id").getValue(String::class.java)
                    val personMeet = jobSnapshot.child("personMeet").getValue(String::class.java)
                    val appointmentTitle = jobSnapshot.child("appointmentTitle").getValue(String::class.java)
                    val eventLocation = jobSnapshot.child("eventLocation").getValue(String::class.java)
                    val tvTime = jobSnapshot.child("tvTime").getValue(String::class.java)
                    val tvSelectDate = jobSnapshot.child("tvSelectDate").getValue(String::class.java)
                    val uid = jobSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && personMeet != null && appointmentTitle != null && eventLocation != null && tvTime != null && tvSelectDate != null && uid != null) {
                        val event = EventData(id, personMeet, appointmentTitle, eventLocation, tvTime, tvSelectDate)
                        eventArrayList.add(event)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }
}
