package com.example.afyamkononi.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.adapters.ExerciseAdapter
import com.example.afyamkononi.databinding.ActivityFitnessBinding
import com.example.afyamkononi.exercise.viewmodel.ExerciseViewModel
import com.example.afyamkononi.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class Fitness : AppCompatActivity() {
    private lateinit var binding: ActivityFitnessBinding
    private lateinit var exerciseAdapter: ExerciseAdapter

    private val exerciseViewModel: ExerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exerciseViewModel.fetchExercises()
        Timber.e("home message here")

        exerciseViewModel.exercises.observe(this, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    val exerciseResult = result.data?.exerciseResult ?: emptyList()
                    exerciseAdapter = ExerciseAdapter(exerciseResult)

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
            }

        })

    }

}
