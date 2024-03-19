package com.example.afyamkononi.doctors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityMotherBinding
import com.example.afyamkononi.doctors.fragments.ChatFragment
import com.example.afyamkononi.doctors.fragments.MeetingsFragment
import com.example.afyamkononi.doctors.fragments.RegisterFragment

class Mother : AppCompatActivity() {
    private lateinit var binding: ActivityMotherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMotherBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(MeetingsFragment())

        binding.docBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.icSchedule -> replaceFragment(MeetingsFragment())
                R.id.icChat -> replaceFragment(ChatFragment())
                R.id.icRegister -> replaceFragment(RegisterFragment())

                else -> {
                }
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.doctorFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}