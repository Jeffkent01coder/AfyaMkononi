package com.example.afyamkononi.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.adapters.DoctorsAdapter
import com.example.afyamkononi.databinding.ActivityDoctorsBinding
import com.example.afyamkononi.model.DoctorData
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Doctors : AppCompatActivity(), DoctorsAdapter.OnDoctorClickListener {
    private lateinit var binding: ActivityDoctorsBinding

    private lateinit var adapter: DoctorsAdapter
    private lateinit var recyclerView: RecyclerView
    private var doctorsArrayList = mutableListOf<DoctorData>()

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDoctors()

        binding.addDoctor.setOnClickListener {
            startActivity(Intent(this, AddDoctor::class.java))
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }



        val layoutManager = LinearLayoutManager(this)
        recyclerView = binding.doctorsRecyler
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DoctorsAdapter(doctorsArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    override fun onDoctorClick(doctor: DoctorData, position: Int) {
        val intent = Intent(this, DoctorInfo::class.java)
        intent.putExtra("doctorName", doctor.doctorName)
        intent.putExtra("doctorProfession", doctor.doctorProfession)
        intent.putExtra("education", doctor.education)
        intent.putExtra("previousRole", doctor.previousRole)
        intent.putExtra("Department", doctor.Department)
        intent.putExtra("Hospital", doctor.Hospital)
        intent.putExtra("time", doctor.time)
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
                            id,
                            doctorName,
                            doctorProfession,
                            education,
                            previousRole,
                            Department,
                            Hospital,
                            time
                        )
                        doctorsArrayList.add(doctor)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }


}