package com.example.afyamkononi.doctors.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.FragmentMeetingsBinding
import com.example.afyamkononi.shared.adapters.EventsAdapter
import com.example.afyamkononi.shared.model.EventData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber


class MeetingsFragment : Fragment(), EventsAdapter.OnEventClickListener {

    private lateinit var binding: FragmentMeetingsBinding

    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventArrayList = mutableListOf<EventData>()

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMeetingsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Fetch and display user name
//        fetchAndDisplayUserName()

        initializeRecyclerView()
        getEvents()


    }

    private fun initializeRecyclerView() {
        recyclerView = binding.doctorEventsRecyclerView
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(eventArrayList, this)
        recyclerView.adapter = adapter
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
                    val doctorId = jobSnapshot.child("doctorId").getValue(String::class.java)
                    val tvSelectDate =
                        jobSnapshot.child("tvSelectDate").getValue(String::class.java)
                    val uid = jobSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && personMeet != null && appointmentTitle != null && eventLocation != null && tvTime != null && tvSelectDate != null && uid != null && doctorId != null) {
                        val event = EventData(
                            id,
                            uid,
                            personMeet,
                            appointmentTitle,
                            eventLocation,
                            tvTime,
                            tvSelectDate,
                            doctorId
                        )
                        Timber.e(event.toString())
                        if (event.doctorId == auth.currentUser?.uid)
                            eventArrayList.add(event)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }

    override fun onEventClick(event: EventData, position: Int) {
        Toast.makeText(requireActivity(), "Event clicked", Toast.LENGTH_LONG).show()

    }

//    private fun fetchAndDisplayUserName() {
//        val currentUser = auth.currentUser
//        val userId = currentUser?.uid
//
//        if (userId != null) {
//            val userRef =
//                FirebaseDatabase.getInstance().getReference("registeredDoctors").child(userId)
//            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        Timber.e("exists")
//                        val userData = snapshot.getValue(DoctorData::class.java)
//                        Timber.e(userData.toString())
//                        if (userData != null) {
//                            val userName = userData.doctorName ?: "Unknown"
//                            Timber.tag("HomeFragment").d("User name retrieved: %s", userName)
//                            binding.userName.text = userName
//                        } else {
//                            Timber.tag("HomeFragment").e("User data is null")
//                        }
//                    } else {
//                        Timber.tag("HomeFragment").e("User data snapshot does not exist")
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle database error
//                    Timber.tag("HomeFragment").e("Failed to fetch user data: %s", error.message)
//                    Toast.makeText(
//                        requireContext(),
//                        "Failed to fetch user data",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//        } else {
//            Timber.tag("HomeFragment").e("Current user ID is null")
//        }
//    }

}