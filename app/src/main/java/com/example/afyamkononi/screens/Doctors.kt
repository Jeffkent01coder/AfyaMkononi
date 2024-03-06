package com.example.afyamkononi.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.R
import com.example.afyamkononi.adapters.DoctorsAdapter
import com.example.afyamkononi.databinding.ActivityDoctorsBinding
import com.example.afyamkononi.model.DoctorData

class Doctors : AppCompatActivity(), DoctorsAdapter.OnDoctorClickListener {
    private lateinit var binding: ActivityDoctorsBinding

    private lateinit var adapter: DoctorsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorsArrayList: ArrayList<DoctorData>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        dataInitialize()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = binding.doctorsRecyler
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DoctorsAdapter(doctorsArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    override fun onDoctorClick(doctor: DoctorData, position: Int) {
        Toast.makeText(this, "Doctor clicked", Toast.LENGTH_SHORT).show()
    }

    private fun dataInitialize() {
        doctorsArrayList = arrayListOf(
            DoctorData(R.drawable.docsimage, "Sean Leaky", "Cardiologist"),
            DoctorData(R.drawable.docsimage, "Peter Onfroy", "Therapist"),
            DoctorData(R.drawable.docsimage, "Jesica Onfroy", "General Doctor"),
            DoctorData(R.drawable.docsimage, "Jeff Leaky", "Massagist"),
            DoctorData(R.drawable.docsimage, "Sean Mendes", "Pediatrician"),
            DoctorData(R.drawable.docsimage, "Lackytisa Leaky", "Radiologist"),
            DoctorData(R.drawable.docsimage, "Megan Leaky", "Cardiologist"),
            DoctorData(R.drawable.docsimage, "Rod Wave", "Therapist"),

            )
    }


}