package com.example.afyamkononi.patients.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.afyamkononi.databinding.ActivityProfileBinding
import com.example.afyamkononi.patients.userAuth.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.editprofile.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        binding.logout.setOnClickListener {
            logOut()
        }

        // Get the current user ID from Firebase Authentication
        val user = FirebaseAuth.getInstance().currentUser
        user?.uid?.let { userId ->
            fetchUserProfile(userId) { userProfile ->
                // Process fetched user profile data here
                if (userProfile != null) {
                    // User profile exists, handle it
                    binding.userPhone.text = userProfile.username
                    binding.userEmail.text = userProfile.email
                    binding.location.text = userProfile.location

                    // Load and display profile image using Glide if available
                    if (!userProfile.image.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(userProfile.image)
                            .into(binding.profileImage)
                    }
                } else {
                    // User profile doesn't exist or fetch failed
                    // Handle the case when the user profile is not found
                    showToast("User profile not found")
                }
            }
        } ?: run {
            // Handle the case when the user is not authenticated
            // For example, redirect to login activity
            showToast("User not authenticated")
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun fetchUserProfile(userId: String, onComplete: (UserProfile?) -> Unit) {
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child("usersProfile").child(userId)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userProfile = dataSnapshot.getValue(UserProfile::class.java)
                    onComplete(userProfile)
                } else {
                    onComplete(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(null)
            }
        })
    }

    data class UserProfile(
        val username: String? = "",
        val image: String? = "",
        val location: String? = "",
        val email: String? = ""
        // Add other profile fields as needed
    )

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun logOut() {
        auth.signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}
