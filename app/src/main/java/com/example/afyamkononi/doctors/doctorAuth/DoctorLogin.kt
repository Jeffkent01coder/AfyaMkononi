package com.example.afyamkononi.doctors.doctorAuth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityDoctorLoginBinding
import com.example.afyamkononi.doctors.Mother
import com.google.firebase.auth.FirebaseAuth

class DoctorLogin : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoctorLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            registerEvents()
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, DoctorRegister::class.java))
            finish()
        }


    }

    private fun registerEvents() {
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passEt.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.emailEt.text?.clear()
                        binding.passEt.text?.clear()
                        Toast.makeText(
                            this,
                            "Logged In successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, Mother::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            } else {
                Toast.makeText(
                    this,
                    "Empty Fields Not Allowed",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }
}