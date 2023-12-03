package com.example.afyamkononi.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityDoctorsBinding

class Doctors : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }


}