package com.example.afyamkononi.screens

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.afyamkononi.Manifest
import com.example.afyamkononi.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

//, OnMapReadyCallback
class HospitalsGPS : AppCompatActivity(){

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_hospitals_gps)

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        Places.initialize(applicationContext, getString(R.string.google_maps_key))
//        placesClient = Places.createClient(this)
    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST_CODE
//            )
//            return
//        }
//
//        map.isMyLocationEnabled = true
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            if (location != null) {
//                val currentLatLng = LatLng(location.latitude, location.longitude)
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//                showNearbyHospitals(location)
//            } else {
//                Toast.makeText(this, "Could not fetch current location", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun showNearbyHospitals(location: android.location.Location) {
//        val request = FindCurrentPlaceRequest.newInstance(listOf(Place.Field.NAME, Place.Field.LAT_LNG))
//        val placeResponse = placesClient.findCurrentPlace(request)
//        placeResponse.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val likelyPlaces = task.result
//                for (placeLikelihood in likelyPlaces?.placeLikelihoods ?: emptyList()) {
//                    val place = placeLikelihood.place
//                    val placeLatLng = place.latLng
//                    if (placeLatLng != null) {
//                        val hospitalMarker = MarkerOptions().position(placeLatLng).title(place.name)
//                        map.addMarker(hospitalMarker)
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Could not fetch nearby hospitals", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                onMapReady(map)
//            } else {
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}