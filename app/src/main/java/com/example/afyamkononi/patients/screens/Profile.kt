package com.example.afyamkononi.patients.screens

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityProfileBinding
import com.example.afyamkononi.patients.userAuth.Login
import com.example.afyamkononi.shared.fire.screens.userside.MyDoctors
import com.example.afyamkononi.shared.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("registeredUser")
        getData()


        binding.logout.setOnClickListener {
            logOut()
        }

        binding.testchat.setOnClickListener {
            startActivity(Intent(this, MyDoctors::class.java))
        }

    }

    private fun getData() {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            // User not authenticated
            return
        }

        // Get a reference to the user data in the database
        val ref = database.child(uid)

        // Retrieve user data from the database
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // User data found, parse it and display in the UI
                    val userData = snapshot.getValue(UserData::class.java)
                    userData?.let {
                        // Update UI with user data
                        binding.name.text = it.name
                        binding.email.text = it.email
                        binding.phone.text = it.phone
                    }
                } else {
                    // User data not found
                    Toast.makeText(this@Profile, "User data not found", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Error fetching user data
                Timber.tag(TAG).e("Failed to fetch user data: %s", error.message)
                Toast.makeText(this@Profile, "Failed to fetch user data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun logOut() {
        auth.signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}
