package com.example.afyamkononi.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityRegisterBinding
import com.example.afyamkononi.screens.Home

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }
}