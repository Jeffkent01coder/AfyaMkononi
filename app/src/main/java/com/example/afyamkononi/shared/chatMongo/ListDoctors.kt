package com.example.afyamkononi.shared.chatMongo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.shared.chatMongo.adapter.DoctorsChatAdapter
import com.example.afyamkononi.databinding.ActivityListDoctorsBinding
import com.example.afyamkononi.shared.model.DoctorData
import com.example.afyamkononi.patients.screens.DoctorInfo
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ListDoctors : AppCompatActivity(), DoctorsChatAdapter.OnDoctorClickListener {
    private lateinit var binding: ActivityListDoctorsBinding

    private lateinit var adapter: DoctorsChatAdapter
    private lateinit var recyclerView: RecyclerView
    private var doctorsChatArrayList = mutableListOf<DoctorData>()

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListDoctorsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDoctors()

        binding.back.setOnClickListener {
            onBackPressed()
        }


        val layoutManager = LinearLayoutManager(this)
        recyclerView = binding.doctorsRecycler
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DoctorsChatAdapter(doctorsChatArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onDoctorClick(doctor: DoctorData, position: Int) {
        val intent = Intent(this, Chat::class.java)
        intent.putExtra("id", doctor.id)
        intent.putExtra("doctorName", doctor.doctorName)
        intent.putExtra("doctorProfession", doctor.doctorProfession)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDoctors() {
        database = Firebase.database.reference
        database.child("Doctors").get()
            .addOnSuccessListener { dataSnapshot ->
                for (doctorSnapshot in dataSnapshot.children) {
                    val id = doctorSnapshot.child("id").getValue(String::class.java)
                    val doctorName = doctorSnapshot.child("doctorName").getValue(String::class.java)
                    val doctorProfession =
                        doctorSnapshot.child("doctorProfession").getValue(String::class.java)
                    val education =
                        doctorSnapshot.child("education").getValue(String::class.java)
                    val previousRole =
                        doctorSnapshot.child("previousRole").getValue(String::class.java)
                    val Department =
                        doctorSnapshot.child("Department").getValue(String::class.java)
                    val Hospital =
                        doctorSnapshot.child("Hospital").getValue(String::class.java)
                    val time =
                        doctorSnapshot.child("time").getValue(String::class.java)
                    val uid = doctorSnapshot.child("uid").getValue(String::class.java)

                    if (id != null && doctorName != null && doctorProfession != null && education != null && previousRole != null && Department != null && Hospital != null && time != null && uid != null) {
                        val doctor = DoctorData(
                            uid,
                            doctorName,
                            doctorProfession,
                            education,
                            previousRole,
                            Department,
                            Hospital,
                            time
                        )
                        doctorsChatArrayList.add(doctor)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }
}