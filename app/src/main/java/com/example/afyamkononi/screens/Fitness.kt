package com.example.afyamkononi.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.adapters.ExerciseAdapter
import com.example.afyamkononi.databinding.ActivityFitnessBinding
import com.example.afyamkononi.exercise.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Fitness : AppCompatActivity() {
    private lateinit var binding: ActivityFitnessBinding
    private lateinit var exerciseAdapter: ExerciseAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseAdapter = ExerciseAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Fitness)
            adapter = exerciseAdapter
        }

        exerciseViewModel = ViewModelProvider(this, viewModelFactory).get(ExerciseViewModel::class.java)

        fetchData()
    }

    private fun fetchData() {
        // Adjust this part to fetch exercises using the ViewModel 37464653damsh941e7c9227e0bf9p1b0871jsnbe2ddf38abba
        exerciseViewModel.fetchExercises(10, "")
        observeExercises()
    }

    private fun observeExercises() {
        exerciseViewModel.exercises.observe(this) { exercises ->
            exercises?.let {
                exerciseAdapter.setData(it)
            }
        }
    }
}
