package com.example.afyamkononi.screens

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityAddDoctorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class AddDoctor : AppCompatActivity() {
    private lateinit var binding : ActivityAddDoctorBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnAdd.setOnClickListener {
            validateData()
        }


    }

    private var doctorName = ""
    private var doctorProfession = ""
    private var education = ""
    private var previousRole = ""
    private var Department = ""
    private var Hospital = ""
    private var time = ""
    private fun validateData() {
        doctorName = binding.doctorName.text.toString().trim()
        doctorProfession = binding.doctorProfession.text.toString().trim()
        education = binding.education.text.toString().trim()
        previousRole = binding.previousRole.text.toString().trim()
        Department = binding.Department.text.toString().trim()
        Hospital = binding.Hospital.text.toString().trim()
        time = binding.time.text.toString().trim()

        if (doctorName.isEmpty()) {
            Toast.makeText(this, "Enter Doctor Name ", Toast.LENGTH_SHORT)
                .show()
        } else if (doctorProfession.isEmpty()) {
            Toast.makeText(this, "Enter Doctor Profession", Toast.LENGTH_SHORT).show()
        } else if (education.isEmpty()) {
            Toast.makeText(this, "Enter Doctor Education ", Toast.LENGTH_SHORT)
                .show()
        } else if (previousRole.isEmpty()) {
            Toast.makeText(this, "Enter Doctor Previous Role", Toast.LENGTH_SHORT).show()
        } else if (Department.isEmpty()) {
            Toast.makeText(this, "Enter Doctor Department", Toast.LENGTH_SHORT).show()
        } else if (Hospital.isEmpty()){
            Toast.makeText(this, "Enter Doctor Hospital", Toast.LENGTH_SHORT).show()
        }else if (time.isEmpty()){
            Toast.makeText(this, "Enter Doctor Availability", Toast.LENGTH_SHORT).show()
        }else {
            uploadDoctorInfoToDb()
            binding.doctorName.text?.clear()
            binding.doctorProfession.text?.clear()
            binding.education.text?.clear()
            binding.previousRole.text?.clear()
            binding.Department.text?.clear()
            binding.Hospital.text?.clear()
            binding.time.text?.clear()
        }

    }

    private fun uploadDoctorInfoToDb() {
        val timeStamp = System.currentTimeMillis()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading data")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["id"] = "$timeStamp"
        hashMap["uid"] = "$uid"
        hashMap["personMeet"] = "$doctorName"
        hashMap["appointmentTitle"] = "$doctorProfession"
        hashMap["eventLocation"] = "$education"
        hashMap["tvTime"] = "$previousRole"
        hashMap["tvSelectDate"] = "$Department"
        hashMap["tvTime"] = "$Hospital"
        hashMap["tvSelectDate"] = "$time"

        val ref = FirebaseDatabase.getInstance().getReference("Doctors")
        ref.child("$timeStamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Uploaded",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Uploading Event Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }
}