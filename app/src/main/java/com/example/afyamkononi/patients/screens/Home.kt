package com.example.afyamkononi.patients.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityHomeBinding
import com.example.afyamkononi.patients.fragments.HomeFragment
import com.example.afyamkononi.patients.fragments.NotificationFragment
import com.example.afyamkononi.patients.fragments.ReportFragment
import com.example.afyamkononi.patients.fragments.ScheduleFragment

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        // Check if the intent contains the extra to navigate directly to the ScheduleFragment
        if (intent.getBooleanExtra("navigateToScheduleFragment", false)) {
            replaceFragment(ScheduleFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.icHome -> replaceFragment(HomeFragment())
                R.id.icSchedule -> replaceFragment(ScheduleFragment())
                R.id.icReport -> replaceFragment(ReportFragment())
                R.id.icNotifications -> replaceFragment(NotificationFragment())

                else -> {
                }
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}