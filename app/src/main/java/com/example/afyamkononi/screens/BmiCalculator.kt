package com.example.afyamkononi.screens

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityBmiCalculatorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BmiCalculator : AppCompatActivity() {
    private lateinit var binding: ActivityBmiCalculatorBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.heightPicker.minValue = 100
        binding.heightPicker.maxValue = 250

        binding.weightPicker.minValue = 30
        binding.weightPicker.maxValue = 150

        binding.weightPicker.setOnValueChangedListener { _, _, _ ->
            calculateBMI()
        }
        binding.heightPicker.setOnValueChangedListener { _, _, _ ->
            calculateBMI()
        }
        binding.btnSaveBmi.setOnClickListener {
            validateData()
        }
    }


    private fun calculateBMI() {
        val height = binding.heightPicker.value
        val doubleHeight = height.toDouble() / 100

        val weight = binding.weightPicker.value
        val bmi = weight.toDouble() / (doubleHeight * doubleHeight)

        binding.tvResults.text = String.format("Your BMI IS : %.2f", bmi)
        binding.tvHealthy.text = String.format("You are considered : %s", healthyMessage(bmi))

    }

    private fun healthyMessage(bmi: Double): String {

        if (bmi < 18.5)
            return "UnderWeight"
        if (bmi < 25.0)
            return "Healthy"
        if (bmi < 30.0)
            return "OverWeight"
        return "Obese"
    }

    private var tvResults = ""
    private var tvHealthy = ""
    private fun validateData() {
        tvResults = binding.tvResults.text.toString().trim()
        tvHealthy = binding.tvHealthy.text.toString().trim()
        if (tvResults.isEmpty()) {
            Toast.makeText(this, "Slide Height and weight", Toast.LENGTH_SHORT)
                .show()
        } else if (tvHealthy.isEmpty()) {
            Toast.makeText(this, "Slide Height and weight", Toast.LENGTH_SHORT).show()
        } else {
            uploadBmiInfoToDb()
        }
    }

    private fun uploadBmiInfoToDb() {
        val timeStamp = System.currentTimeMillis()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading data")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["id"] = "$timeStamp"
        hashMap["uid"] = "$uid"
        hashMap["tvResults"] = "$tvResults"
        hashMap["tvComments"] = "$tvHealthy"

        val ref = FirebaseDatabase.getInstance().getReference("BMI_Data")
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