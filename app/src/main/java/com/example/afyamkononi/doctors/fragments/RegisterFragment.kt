package com.example.afyamkononi.doctors.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afyamkononi.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
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
            Toast.makeText(requireActivity(), "Enter Doctor Name ", Toast.LENGTH_SHORT)
                .show()
        } else if (doctorProfession.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Doctor Profession", Toast.LENGTH_SHORT).show()
        } else if (education.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Doctor Education ", Toast.LENGTH_SHORT)
                .show()
        } else if (previousRole.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Doctor Previous Role", Toast.LENGTH_SHORT).show()
        } else if (Department.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Doctor Department", Toast.LENGTH_SHORT).show()
        } else if (Hospital.isEmpty()){
            Toast.makeText(requireActivity(), "Enter Doctor Hospital", Toast.LENGTH_SHORT).show()
        }else if (time.isEmpty()){
            Toast.makeText(requireActivity(), "Enter Doctor Availability", Toast.LENGTH_SHORT).show()
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

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading data")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["id"] = "$timeStamp"
        hashMap["uid"] = "$uid"
        hashMap["doctorName"] = "$doctorName"
        hashMap["doctorProfession"] = "$doctorProfession"
        hashMap["education"] = "$education"
        hashMap["previousRole"] = "$previousRole"
        hashMap["Department"] = "$Department"
        hashMap["Hospital"] = "$Hospital"
        hashMap["time"] = "$time"

        val ref = FirebaseDatabase.getInstance().getReference("Doctors")
        ref.child("$timeStamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    requireActivity(),
                    "Uploaded",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    requireActivity(),
                    "Uploading Event Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }


}