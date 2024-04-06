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
import com.example.afyamkononi.databinding.FragmentReportBinding
import com.example.afyamkononi.patients.report.Reports
import com.example.afyamkononi.shared.adapters.EventsAdapter
import com.example.afyamkononi.shared.model.BMIData
import com.example.afyamkononi.shared.model.EventData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment(), EventsAdapter.OnEventClickListener {
    private lateinit var binding: FragmentReportBinding
    private lateinit var adapter: EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventArrayList = mutableListOf<EventData>()

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnReport.setOnClickListener {
            val intent =Intent(requireActivity(), Reports::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        // Initialize database reference
        database = FirebaseDatabase.getInstance().reference.child("UpcomingEvents")

        getBmiData(auth.currentUser!!.uid)

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
        val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        Timber.d("Current Date: $currentDate")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {
                    val id = jobSnapshot.child("id").getValue(String::class.java)
                    val personMeet = jobSnapshot.child("personMeet").getValue(String::class.java)
                    val appointmentTitle = jobSnapshot.child("appointmentTitle").getValue(String::class.java)
                    val eventLocation = jobSnapshot.child("eventLocation").getValue(String::class.java)
                    val tvTime = jobSnapshot.child("tvTime").getValue(String::class.java)
                    val tvSelectDate = jobSnapshot.child("tvSelectDate").getValue(String::class.java)
                    val doctorId = jobSnapshot.child("doctorId").getValue(String::class.java)
                    val uid = jobSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && personMeet != null && appointmentTitle != null && eventLocation != null && tvTime != null && tvSelectDate != null && uid != null && doctorId != null) {
                        val event = EventData(id, uid, personMeet, appointmentTitle, eventLocation, tvTime, tvSelectDate, doctorId)
                        Timber.d("Event Date: $tvSelectDate")

                        // Assuming tvSelectDate is in "MM/dd/yyyy" format
                        if (event.uid == auth.currentUser?.uid && isDateBeforeCurrent(tvSelectDate, currentDate)) {
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

    private fun isDateBeforeCurrent(dateToCheck: String?, currentDate: String): Boolean {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date1 = sdf.parse(dateToCheck)
        val date2 = sdf.parse(currentDate)

        // Compare year, month, and day components
        return date1.before(date2)
    }




    private fun getBmiData(currentUserUid: String) {
        val database = FirebaseDatabase.getInstance().reference.child("BMI_Data")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (bmiSnapshot in dataSnapshot.children) {
                        val uid = bmiSnapshot.child("uid").getValue(String::class.java)
                        if (uid == currentUserUid) {
                            val id = bmiSnapshot.child("id").getValue(String::class.java)
                            val tvResults =
                                bmiSnapshot.child("tvResults").getValue(String::class.java)
                            val tvHealthy =
                                bmiSnapshot.child("tvHealthy").getValue(String::class.java)

                            Timber.e(id)
                            Timber.e(uid)
                            Timber.e(tvHealthy)
                            Timber.e(tvResults)
                            if (id != null && tvResults != null && tvHealthy != null) {
                                val bmiData = BMIData(id, uid, tvResults, tvHealthy)
                                Timber.e("$bmiData")

                                requireActivity().runOnUiThread {
                                    binding.tvResults.text = bmiData.tvResults
                                    binding.tvHealthy.text = bmiData.tvHealthy
                                }

                                // Assuming you have only one BMI data entry, you can break the loop after updating UI
                                return
                            }
                        }
                    }
                    // If BMI data for the current user is not found
                    Toast.makeText(
                        requireActivity(),
                        "No BMI data found for the current user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // If no BMI data exists in the database
                    Toast.makeText(
                        requireActivity(),
                        "BMI data not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Toast.makeText(
                    requireActivity(),
                    "Failed to retrieve BMI data: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}