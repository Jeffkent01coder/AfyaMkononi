package com.example.afyamkononi.fragments

import android.annotation.SuppressLint
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
import com.example.afyamkononi.R
import com.example.afyamkononi.adapters.EventsAdapter
import com.example.afyamkononi.chatMongo.ListDoctors
import com.example.afyamkononi.databinding.FragmentHomeBinding
import com.example.afyamkononi.model.EventData
import com.example.afyamkononi.model.UserData
import com.example.afyamkononi.screens.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment(), EventsAdapter.OnEventClickListener {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventArrayList = mutableListOf<EventData>()


    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Fetch and display user name
        fetchAndDisplayUserName()

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
        binding.chat.setOnClickListener {
            val intent = Intent(requireActivity(), ListDoctors::class.java)
            startActivity(intent)
        }



        binding.fitness.setOnClickListener {
            val intent = Intent(requireActivity(), Fitness::class.java)
            startActivity(intent)
        }
        binding.clinics.setOnClickListener {
            val intent = Intent(requireActivity(), HospitalsGPS::class.java)
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

    override fun onEventClick(event: EventData, position: Int) {
        Toast.makeText(requireActivity(), "Event clicked", Toast.LENGTH_LONG).show()
    }

    private fun fetchAndDisplayUserName() {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("registeredUser").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userData = snapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val userName = userData.name ?: "Unknown"
                            Timber.tag("HomeFragment").d("User name retrieved: %s", userName)
                            binding.userName.text = userName
                        } else {
                            Timber.tag("HomeFragment").e("User data is null")
                        }
                    } else {
                        Timber.tag("HomeFragment").e("User data snapshot does not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    Timber.tag("HomeFragment").e("Failed to fetch user data: %s", error.message)
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Timber.tag("HomeFragment").e("Current user ID is null")
        }
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
