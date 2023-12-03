package com.example.afyamkononi.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityHomeBinding
import com.example.afyamkononi.fragments.HomeFragment
import com.example.afyamkononi.fragments.NotificationFragment
import com.example.afyamkononi.fragments.ReportFragment
import com.example.afyamkononi.fragments.ScheduleFragment

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

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