package com.example.afyamkononi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityChoiceBinding
import com.example.afyamkononi.doctors.doctorAuth.DoctorLogin
import com.example.afyamkononi.userAuth.Login

class Choice : AppCompatActivity() {
    private lateinit var binding: ActivityChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnDoctor.setOnClickListener {
            startActivity(Intent(this, DoctorLogin::class.java))
            finish()
        }
        binding.btnPatient.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}