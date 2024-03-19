package com.example.afyamkononi.patients.exercise.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afyamkononi.patients.exercise.model.Exercise
import com.example.afyamkononi.patients.exercise.retrofit.Repository
import com.example.afyamkononi.patients.exercise.util.Resource
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val repository: Repository = Repository()
    private val _exercises: MutableLiveData<Resource<List<Exercise>>> =
        MutableLiveData<Resource<List<Exercise>>>().apply {
            value = Resource.Loading() // Set initial value as loading
        }
    val exercises: LiveData<Resource<List<Exercise>>> = _exercises


    init {
        viewModelScope.launch {
            val apiResponse = repository.getExercises()
            _exercises.value = apiResponse
        }
    }
}