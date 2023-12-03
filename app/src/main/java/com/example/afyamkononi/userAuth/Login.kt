package com.example.afyamkononi.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityLoginBinding
import com.example.afyamkononi.screens.Home

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
         binding.btnRegister.setOnClickListener {
             startActivity(Intent(this, Register::class.java))
             finish()
         }
    }
}