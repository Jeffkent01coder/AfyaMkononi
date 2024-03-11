package com.example.afyamkononi.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityHospitalsGpsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true

        // Assume getNearbyHospitals() returns a list of LatLng representing nearby hospitals
        val nearbyHospitals = getNearbyHospitals()
        for (hospital in nearbyHospitals) {
            addMarker(hospital, "Hospital")
        }

        // Zoom to a reasonable level to display all markers
        if (nearbyHospitals.isNotEmpty()) {
            val firstHospital = nearbyHospitals[0]
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstHospital, 12f))
        }
    }

    private fun addMarker(latLng: LatLng, title: String) {
        map.addMarker(MarkerOptions().position(latLng).title(title))
    }

    private fun getNearbyHospitals(): List<LatLng> {
        val hospitals = mutableListOf<LatLng>()

        // Hospitals in Tigania West
        hospitals.add(LatLng(-0.063, 37.641)) // Hospital 1 in Tigania West
        hospitals.add(LatLng(-0.069, 37.650)) // Hospital 2 in Tigania West
        hospitals.add(LatLng(-0.072, 37.658)) // Hospital 3 in Tigania West
        hospitals.add(LatLng(-0.074, 37.663)) // Hospital 4 in Tigania West
        hospitals.add(LatLng(-0.078, 37.670)) // Hospital 5 in Tigania West
        hospitals.add(LatLng(-0.083, 37.675)) // Hospital 6 in Tigania West
        hospitals.add(LatLng(-0.086, 37.680)) // Hospital 7 in Tigania West
        hospitals.add(LatLng(-0.090, 37.685)) // Hospital 8 in Tigania West
        hospitals.add(LatLng(-0.095, 37.690)) // Hospital 9 in Tigania West
        hospitals.add(LatLng(-0.100, 37.695)) // Hospital 10 in Tigania West
        // Add more hospitals from Tigania West as needed

        // Additional Hospitals
        hospitals.add(LatLng(-0.155, 37.598)) // Consolata Nkubu Mission Hospital
        hospitals.add(LatLng(-0.162, 37.596)) // Cottolengo Mission Hospital - Chaaria
        hospitals.add(LatLng(0.076, 37.682)) // Dorjos Health Services
        hospitals.add(LatLng(0.070, 37.654)) // Karen Hospital - Meru
        hospitals.add(LatLng(0.066, 37.662)) // Nairobi Women's Hospital - Meru
        hospitals.add(LatLng(0.159, 37.651)) // PCEA Chogoria Hospital
        hospitals.add(LatLng(-0.035, 37.811)) // St John of God Tigania Hospital
        hospitals.add(LatLng(0.078, 37.637)) // St Lukes Cottage Hospital
        hospitals.add(LatLng(0.067, 37.656)) // Meru Level 5 Hospital
        hospitals.add(LatLng(0.068, 37.654)) // Aga Khan University Hospital

        // Hospitals around Aina Children's Home
        hospitals.add(LatLng(0.082, 37.616)) // Hospital 1 near Aina Children's Home
        hospitals.add(LatLng(0.080, 37.620)) // Hospital 2 near Aina Children's Home
        hospitals.add(LatLng(0.078, 37.624)) // Hospital 3 near Aina Children's Home
        hospitals.add(LatLng(0.076, 37.628)) // Hospital 4 near Aina Children's Home
        hospitals.add(LatLng(0.074, 37.632)) // Hospital 5 near Aina Children's Home
        // Add more hospitals around Aina Children's Home as needed

        // Hospitals around KFC Meru
        hospitals.add(LatLng(0.051, 37.640)) // Hospital 1 near KFC Meru
        hospitals.add(LatLng(0.053, 37.644)) // Hospital 2 near KFC Meru
        hospitals.add(LatLng(0.055, 37.648)) // Hospital 3 near KFC Meru
        hospitals.add(LatLng(0.057, 37.652)) // Hospital 4 near KFC Meru
        hospitals.add(LatLng(0.059, 37.656)) // Hospital 5 near KFC Meru
        // Add more hospitals around KFC Meru as needed

        // Hospitals around Meru University of Science and Technology (MUST)
        hospitals.add(LatLng(0.084, 37.656)) // Hospital 1 near MUST
        hospitals.add(LatLng(0.082, 37.660)) // Hospital 2 near MUST
        hospitals.add(LatLng(0.080, 37.664)) // Hospital 3 near MUST
        hospitals.add(LatLng(0.078, 37.668)) // Hospital 4 near MUST
        hospitals.add(LatLng(0.076, 37.672)) // Hospital 5 near MUST
        // Add more hospitals around MUST as needed

        hospitals.add(LatLng(-0.026, 37.710)) // Hospital 1 in Tigania East
        hospitals.add(LatLng(-0.030, 37.720)) // Hospital 2 in Tigania East
        hospitals.add(LatLng(-0.035, 37.730)) // Hospital 3 in Tigania East
        hospitals.add(LatLng(-0.040, 37.740)) // Hospital 4 in Tigania East
        hospitals.add(LatLng(-0.045, 37.750)) // Hospital 5 in Tigania East
        hospitals.add(LatLng(-0.050, 37.760)) // Hospital 6 in Tigania East
        hospitals.add(LatLng(-0.055, 37.770)) // Hospital 7 in Tigania East
        hospitals.add(LatLng(-0.060, 37.780)) // Hospital 8 in Tigania East
        hospitals.add(LatLng(-0.065, 37.790)) // Hospital 9 in Tigania East
        hospitals.add(LatLng(-0.070, 37.800)) // Hospital 10 in Tigania East
        // Add more hospitals from Tigania East as needed

        // Hospitals in Imenti
        hospitals.add(LatLng(0.072, 37.615)) // Hospital 1 in Imenti
        hospitals.add(LatLng(0.078, 37.620)) // Hospital 2 in Imenti
        hospitals.add(LatLng(0.082, 37.625)) // Hospital 3 in Imenti
        hospitals.add(LatLng(0.086, 37.630)) // Hospital 4 in Imenti
        hospitals.add(LatLng(0.090, 37.635)) // Hospital 5 in Imenti
        hospitals.add(LatLng(0.094, 37.640)) // Hospital 6 in Imenti
        hospitals.add(LatLng(0.098, 37.645)) // Hospital 7 in Imenti
        hospitals.add(LatLng(0.102, 37.650)) // Hospital 8 in Imenti
        hospitals.add(LatLng(0.106, 37.655)) // Hospital 9 in Imenti
        hospitals.add(LatLng(0.110, 37.660)) // Hospital 10 in Imenti

        // Hospitals in Meru Town
        hospitals.add(LatLng(-0.065, 37.645)) // Hospital 1 in Meru Town
        hospitals.add(LatLng(-0.070, 37.652)) // Hospital 2 in Meru Town
        hospitals.add(LatLng(-0.075, 37.659)) // Hospital 3 in Meru Town
        hospitals.add(LatLng(-0.080, 37.666)) // Hospital 4 in Meru Town
        hospitals.add(LatLng(-0.085, 37.673)) // Hospital 5 in Meru Town
        hospitals.add(LatLng(-0.090, 37.680)) // Hospital 6 in Meru Town
        hospitals.add(LatLng(-0.095, 37.687)) // Hospital 7 in Meru Town
        hospitals.add(LatLng(-0.100, 37.694)) // Hospital 8 in Meru Town
        hospitals.add(LatLng(-0.105, 37.701)) // Hospital 9 in Meru Town
        hospitals.add(LatLng(-0.110, 37.708)) // Hospital 10 in Meru Town
        // Add more hospitals from Meru Town as needed

        // Hospitals in other parts of Kenya
        hospitals.add(LatLng(-1.283, 36.817)) // Hospital in Nairobi
        hospitals.add(LatLng(-0.281, 36.070)) // Hospital in Nakuru
        hospitals.add(LatLng(-0.358, 35.282)) // Hospital in Eldoret
        // Add more hospitals from other parts of Kenya
        hospitals.add(LatLng(-1.292, 36.821))
        hospitals.add(LatLng(-1.301, 36.821))
        hospitals.add(LatLng(-1.286, 36.815))
        hospitals.add(LatLng(-1.280, 36.824))
        hospitals.add(LatLng(-1.281, 36.816))
        hospitals.add(LatLng(-1.298, 36.819))
        hospitals.add(LatLng(-1.295, 36.810))
        hospitals.add(LatLng(-1.289, 36.828))
        hospitals.add(LatLng(-1.284, 36.813))
        hospitals.add(LatLng(-1.287, 36.823))
        hospitals.add(LatLng(-1.300, 36.818))
        hospitals.add(LatLng(-1.294, 36.822))
        hospitals.add(LatLng(-1.282, 36.827))
        hospitals.add(LatLng(-1.290, 36.812))
        hospitals.add(LatLng(-1.297, 36.824))
        hospitals.add(LatLng(-1.283, 36.815))
        hospitals.add(LatLng(-1.299, 36.813))
        hospitals.add(LatLng(-1.285, 36.814))
        hospitals.add(LatLng(-1.293, 36.820))
        hospitals.add(LatLng(-1.281, 36.822))
        hospitals.add(LatLng(-1.292, 36.816))
        hospitals.add(LatLng(-1.280, 36.815))
        hospitals.add(LatLng(-1.299, 36.816))
        hospitals.add(LatLng(-1.283, 36.819))
        hospitals.add(LatLng(-1.291, 36.813))
        hospitals.add(LatLng(-1.284, 36.817))
        hospitals.add(LatLng(-1.288, 36.818))
        hospitals.add(LatLng(-1.296, 36.814))
        hospitals.add(LatLng(-1.282, 36.818))
        hospitals.add(LatLng(-1.294, 36.816))
        hospitals.add(LatLng(-1.287, 36.815))
        hospitals.add(LatLng(-1.298, 36.815))
        hospitals.add(LatLng(-1.285, 36.817))
        hospitals.add(LatLng(-1.290, 36.820))
        hospitals.add(LatLng(-1.281, 36.814))

        return hospitals
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
}
