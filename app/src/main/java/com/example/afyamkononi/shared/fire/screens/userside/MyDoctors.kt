package com.example.afyamkononi.shared.fire.screens.userside

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.ActivityMyDoctorsBinding
import com.example.afyamkononi.shared.chatMongo.Chat
import com.example.afyamkononi.shared.chatMongo.adapter.DoctorsChatAdapter
import com.example.afyamkononi.shared.fire.screens.MyChats
import com.example.afyamkononi.shared.model.DoctorData
import com.google.firebase.Firebase
import com.google.firebase.database.*

class MyDoctors : AppCompatActivity(), DoctorsChatAdapter.OnDoctorClickListener {

    private lateinit var binding: ActivityMyDoctorsBinding

    private lateinit var adapter: DoctorsChatAdapter
    private lateinit var recyclerView: RecyclerView
    private var doctorsChatArrayList = mutableListOf<DoctorData>()

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        initializeRecyclerView()
//        getDoctors()
//
//        binding.back.setOnClickListener {
//            onBackPressed()
//        }
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

//    private fun initializeRecyclerView() {
//        recyclerView = binding.doctorsRecycler
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.setHasFixedSize(true)
//        adapter = DoctorsChatAdapter(doctorsChatArrayList, this)
//        recyclerView.adapter = adapter
//    }

    override fun onDoctorClick(doctor: DoctorData, position: Int) {
        val intent = Intent(this, MyChats::class.java)
        intent.putExtra("id", doctor.uid)
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
