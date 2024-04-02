package com.example.afyamkononi.patients.fragments

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
import com.example.afyamkononi.databinding.FragmentHomeBinding
import com.example.afyamkononi.patients.ai.screens.Ai
import com.example.afyamkononi.patients.machineLearning.ScanResult
import com.example.afyamkononi.patients.screens.*
import com.example.afyamkononi.shared.adapters.EventsAdapter
import com.example.afyamkononi.shared.fire.screens.userside.MyDoctors
import com.example.afyamkononi.shared.model.EventData
import com.example.afyamkononi.shared.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber
import java.text.SimpleDateFormat
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
        database = FirebaseDatabase.getInstance().reference.child("UpcomingEvents")

        // Fetch and display user name
        fetchAndDisplayUserName()

        getEvents()

        // Initialize RecyclerView and adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.eventsRecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(eventArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        // Set click listeners for other UI elements
        binding.bmi.setOnClickListener {
            startActivity(Intent(requireActivity(), BmiCalculator::class.java))
        }
        binding.machineLearning.setOnClickListener {
            startActivity(Intent(requireActivity(), ScanResult::class.java))
        }
        // Set click listeners for other UI elements
        binding.news.setOnClickListener {
            startActivity(Intent(requireActivity(), News::class.java))
        }
        // Set click listeners for other UI elements
        binding.doctors.setOnClickListener {
            startActivity(Intent(requireActivity(), Doctors::class.java))
        }
        // Set click listeners for other UI elements
        binding.chat.setOnClickListener {
            startActivity(Intent(requireActivity(), MyDoctors::class.java))
        }
        // Set click listeners for other UI elements
        binding.ai.setOnClickListener {
            startActivity(Intent(requireActivity(), Ai::class.java))
        }
        // Set click listeners for other UI elements
        binding.fitness.setOnClickListener {
            startActivity(Intent(requireActivity(), Fitness::class.java))
        }
        // Set click listeners for other UI elements
        binding.clinics.setOnClickListener {
            startActivity(Intent(requireActivity(), HospitalsGPS::class.java))
        }
        // Set click listeners for other UI elements
        binding.profileImage.setOnClickListener {
            startActivity(Intent(requireActivity(), Profile::class.java))
        }
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
                        Timber.d("User data snapshot exists")
                        val userData = snapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val userName = userData.name ?: "Unknown"
                            Timber.d("User name retrieved: $userName")
                            binding.userName.text = userName
                        } else {
                            Timber.e("User data is null")
                        }
                    } else {
                        Timber.e("User data snapshot does not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.e("Failed to fetch user data: ${error.message}")
                    Toast.makeText(requireContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Timber.e("Current user ID is null")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getEvents() {
        database = FirebaseDatabase.getInstance().reference.child("UpcomingEvents")
        val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        Timber.d("Current Date: $currentDate")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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
                            uid,
                            personMeet,
                            appointmentTitle,
                            eventLocation,
                            tvTime,
                            tvSelectDate
                        )
                        Timber.d("Event Date: $tvSelectDate")

                        // Assuming tvSelectDate is in "MM/dd/yyyy" format
                        if (event.uid == auth.currentUser?.uid && isDateAfterCurrent(
                                tvSelectDate,
                                currentDate
                            )
                        ) {
                            eventArrayList.add(event)
                            Timber.d("Event added: $event")
                        } else {
                            Timber.d("Event skipped: $event")
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Timber.e("Failed to fetch events: ${databaseError.message}")
            }
        })
    }

    private fun isDateAfterCurrent(dateToCheck: String?, currentDate: String): Boolean {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date1 = sdf.parse(dateToCheck)
        val date2 = sdf.parse(currentDate)

        // Compare year, month, and day components
        return date1.after(date2)
    }




}
