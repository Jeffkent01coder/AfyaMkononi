package com.example.afyamkononi.patients.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityEditProfileBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private var selectedImageUri: Uri? = null
    private lateinit var storageReference: StorageReference

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.back.setOnClickListener {
            onBackPressed()
        }

        storageReference = FirebaseStorage.getInstance().reference.child("user_images")

        binding.profileImage.setOnClickListener {
            openGallery()
        }

        binding.save.setOnClickListener {
            updateProfile()
        }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST)
    }

    private fun updateProfile() {
        val username = binding.userPhone.text.toString()
        val location = binding.location.text.toString()
        val email = binding.emailEt.text.toString()

        if (selectedImageUri != null) {
            uploadImage(selectedImageUri!!) { imageUrl ->
                saveUserData(username, location, email, imageUrl) // Pass location and email here
            }
        } else {
            saveUserData(username, location, email, null) // Pass location and email here
        }
    }

    private fun uploadImage(imageUri: Uri, onComplete: (String) -> Unit) {
        val imageRef = storageReference.child("${UUID.randomUUID()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    onComplete(uri.toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserData(username: String, location: String, email: String, imageUrl: String?) {
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child("usersProfile").push()
        val userMap = HashMap<String, Any>()
        userMap["username"] = username
        imageUrl?.let { userMap["image"] = it }
        userMap["location"] = location
        userMap["email"] = email
        // Add other profile information to userMap

        databaseReference.updateChildren(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.location.text?.clear()
                    binding.emailEt.text?.clear()
                    binding.userPhone.text?.clear()
                    binding.profileImage.toString()
                    binding.profileImage.setImageDrawable(null)
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.profileImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST = 100
    }

}