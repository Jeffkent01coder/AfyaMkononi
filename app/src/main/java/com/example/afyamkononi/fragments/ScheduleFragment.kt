package com.example.afyamkononi.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.afyamkononi.databinding.FragmentScheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    lateinit var tvDate: TextView
    lateinit var btnshowdatepicker: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.N)
    private val calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        tvDate = binding.tvSelectDate
        btnshowdatepicker = binding.btnshowDatePicker

        btnshowdatepicker.setOnClickListener {
            showDatePicker()
        }

        binding.btnSaveEvent.setOnClickListener {
            validateData()
        }


        val btnPickTime = binding.btnPickTime
        val tvTime = binding.tvTime

        btnPickTime.setOnClickListener {
            val cal = java.util.Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.HOUR_OF_DAY, minute)
                tvTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                requireActivity(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireActivity(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate: String = dateFormat.format(selectedDate.time)
                tvDate.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


    private var personMeet = ""
    private var appointmentTitle = ""
    private var eventLocation = ""
    private var tvTime = ""
    private var tvSelectDate = ""
    private fun validateData() {
        Log.d(TAG, "Validating Data")
        personMeet = binding.personMeet.text.toString().trim()
        appointmentTitle = binding.appointmentTitle.text.toString().trim()
        eventLocation = binding.eventLocation.text.toString().trim()
        tvTime = binding.tvTime.text.toString().trim()
        tvSelectDate = binding.tvSelectDate.text.toString().trim()

        if (personMeet.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Name of person Meeting ", Toast.LENGTH_SHORT)
                .show()
        } else if (appointmentTitle.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Appointment Title", Toast.LENGTH_SHORT).show()
        } else if (eventLocation.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter Appointment Location ", Toast.LENGTH_SHORT)
                .show()
        } else if (tvTime.isEmpty()) {
            Toast.makeText(requireActivity(), "Pick Event Time", Toast.LENGTH_SHORT).show()
        } else if (tvSelectDate.isEmpty()) {
            Toast.makeText(requireActivity(), "Pick Event Date", Toast.LENGTH_SHORT).show()
        } else {
            uploadEventInfoToDb()
            binding.personMeet.text?.clear()
            binding.appointmentTitle.text?.clear()
            binding.eventLocation.text?.clear()
        }


    }

    private fun uploadEventInfoToDb() {
        val timeStamp = System.currentTimeMillis()

        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Uploading data")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["id"] = "$timeStamp"
        hashMap["uid"] = "$uid"
        hashMap["personMeet"] = "$personMeet"
        hashMap["appointmentTitle"] = "$appointmentTitle"
        hashMap["eventLocation"] = "$eventLocation"
        hashMap["tvTime"] = "$tvTime"
        hashMap["tvSelectDate"] = "$tvSelectDate"

        val ref = FirebaseDatabase.getInstance().getReference("UpcomingEvents")
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