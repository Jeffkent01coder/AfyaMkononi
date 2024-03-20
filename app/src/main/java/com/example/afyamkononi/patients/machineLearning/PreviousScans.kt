package com.example.afyamkononi.patients.machineLearning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.ActivityPreviousScansBinding
import com.example.afyamkononi.patients.machineLearning.adapter.ScanAdapter
import com.example.afyamkononi.patients.machineLearning.model.ScanData

class PreviousScans : AppCompatActivity() {
    private lateinit var binding: ActivityPreviousScansBinding


    private lateinit var adapter: ScanAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var scanArrayList: MutableList<ScanData>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPreviousScansBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.addDoctor.setOnClickListener {
            startActivity(Intent(this, ScanResult::class.java))
        }

        dataInitialize()
        val layoutManager = LinearLayoutManager(this)
        recyclerView = binding.scanRecycler
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ScanAdapter(scanArrayList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun dataInitialize() {
        scanArrayList = arrayListOf(
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),
            ScanData("Cancer"),
            ScanData("Leach"),
            ScanData("Futgen"),


            )
    }
}