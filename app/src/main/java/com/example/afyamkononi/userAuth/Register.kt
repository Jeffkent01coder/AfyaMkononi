package com.example.afyamkononi.userAuth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityRegisterBinding
import com.example.afyamkononi.screens.Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

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
    private var user = ""
    private var phone = ""
    private fun registerEvents() {
        binding.btnRegister.setOnClickListener {
            email = binding.userEmail.text.toString().trim()
            user = binding.userName.text.toString().trim()
            phone = binding.userPhone.text.toString().trim()
            val pass = binding.passEt.text.toString().trim()
            val verifyPass = binding.verifyPassEt.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty() && user.isNotEmpty() && phone.isNotEmpty()) {
                if (pass == verifyPass) {
                    binding.progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            createUserDetails(timeStamp)

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
                    }
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

    val timeStamp = System.currentTimeMillis()
    private fun createUserDetails(timeStamp: Long) {
        Timber.tag(TAG).d("Uploading to Database")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()

        hashMap["uid"] = "$uid"
        hashMap["Name"] = "$user"
        hashMap["Email"] = "$email"
        hashMap["PhoneNumber"] = "$phone"

        val ref = FirebaseDatabase.getInstance().getReference("registeredUser")
        ref.child("$timeStamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                Timber.tag(TAG).d("Registered")
                Toast.makeText(
                    this,
                    "Registered Successfully",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener { e ->
                Timber.tag(TAG).d("Uploading to Storage Failed due to %s", e.message)
                Toast.makeText(
                    this,
                    "Registration Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }
}