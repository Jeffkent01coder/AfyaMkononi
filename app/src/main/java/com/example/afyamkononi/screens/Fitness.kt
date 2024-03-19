package com.example.afyamkononi.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.afyamkononi.adapters.ExerciseAdapter
import com.example.afyamkononi.databinding.ActivityFitnessBinding
import com.example.afyamkononi.exercise.ui.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fitness : AppCompatActivity() {
    private lateinit var binding: ActivityFitnessBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var exerciseViewModel: ExerciseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseViewModel = ExerciseViewModel()

        exerciseViewModel.exercises.observe(this, Observer { result ->

        })

    }

}
