package com.example.afyamkononi.userAuth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityRegisterBinding
import com.example.afyamkononi.screens.Home
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()
        registerEvents()

    }

    private var email = ""
    private fun registerEvents() {
        binding.btnRegister.setOnClickListener {
            email = binding.userEmail.text.toString().trim()
            val pass = binding.passEt.text.toString().trim()
            val verifyPass = binding.verifyPassEt.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                if (pass == verifyPass) {
                    binding.progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful) {
                                binding.userName.text?.clear()
                                binding.userPhone.text?.clear()
                                binding.passEt.text?.clear()
                                binding.verifyPassEt.text?.clear()
                                binding.userEmail.text?.clear()
                                Toast.makeText(
                                    this,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                                ).show()


                            } else {
                                Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            if (email.isNotEmpty() && pass.isNotEmpty()) {
                                startActivity(Intent(this, Home::class.java))
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please fill in all fields",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            binding.progressBar.visibility = View.GONE
                        })
                } else {
                    Toast.makeText(
                        this,
                        "Passwords Don't Match",
                        Toast.LENGTH_SHORT
                    ).show()
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