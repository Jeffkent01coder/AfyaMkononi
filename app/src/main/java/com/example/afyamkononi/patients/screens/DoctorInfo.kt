package com.example.afyamkononi.patients.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityDoctorInfoBinding

class DoctorInfo : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.apply {
            intent
            doctorName.text = intent.getStringExtra("doctorName")
            doctorProfession.text = intent.getStringExtra("doctorProfession")
            education.text = intent.getStringExtra("education")
            previousRole.text = intent.getStringExtra("previousRole")
            Department.text = intent.getStringExtra("Department")
            Hospital.text = intent.getStringExtra("Hospital")
            time.text = intent.getStringExtra("time")
        }

        binding.btnBook.setOnClickListener {
            // Start the Home activity
            val intent = Intent(this@DoctorInfo, Home::class.java)
            // Pass an extra to indicate that we want to navigate to the ScheduleFragment directly
            intent.putExtra("navigateToScheduleFragment", true)
            startActivity(intent)
        }


    }

}