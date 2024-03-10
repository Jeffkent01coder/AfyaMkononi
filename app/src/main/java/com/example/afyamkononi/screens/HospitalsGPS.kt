package com.example.afyamkononi.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityHospitalsGpsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class HospitalsGPS : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHospitalsGpsBinding
    private lateinit var map: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    //AIzaSyBysSedUGsoKsG0NrqvFGwshrFzAmdCLWM

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHospitalsGpsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this) ?: run {
            val newMapFragment = SupportMapFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.map, newMapFragment).commit()
            newMapFragment.getMapAsync(this)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }

}