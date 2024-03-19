package com.example.afyamkononi.patients.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.shared.adapters.ExerciseAdapter
import com.example.afyamkononi.databinding.ActivityFitnessBinding
import com.example.afyamkononi.patients.exercise.ui.ExerciseViewModel
import com.example.afyamkononi.patients.exercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fitness : AppCompatActivity() {
    private lateinit var binding: ActivityFitnessBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        exerciseViewModel = ExerciseViewModel()

        exerciseViewModel.exercises.observe(this, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    val exerciseList = result.data
                    exerciseAdapter = ExerciseAdapter(exerciseList ?: emptyList())

                    binding.recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@Fitness)
                        setHasFixedSize(true)
                        adapter = exerciseAdapter
                    }
                    exerciseAdapter.notifyDataSetChanged()
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                else -> {}
            }
        })

    }
}
