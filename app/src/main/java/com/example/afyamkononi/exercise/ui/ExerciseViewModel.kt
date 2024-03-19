package com.example.afyamkononi.exercise.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afyamkononi.exercise.model.Exercise
import com.example.afyamkononi.exercise.retrofit.Repository
import com.example.afyamkononi.exercise.util.Resource
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val repository: Repository = Repository()
    val exercises: MutableLiveData<List<Exercise>> = MutableLiveData()

    init {
        viewModelScope.launch {
            when (val apiResponse = repository.getExercises()) {
                is Resource.Loading -> {

                }

                is Error -> {

                }

                is Resource.Success -> {
                    exercises.value = apiResponse.data ?: emptyList()
                }
                else -> {}
            }
        }
    }
}