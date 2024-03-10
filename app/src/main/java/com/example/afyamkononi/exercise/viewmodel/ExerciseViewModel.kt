package com.example.afyamkononi.exercise.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem
import com.example.afyamkononi.exercise.exercise.model.ExerciseResult
import com.example.afyamkononi.exercise.repository.ExerciseDataRepository
import com.example.afyamkononi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor( val repository: ExerciseDataRepository) :
    ViewModel() {

    private val _exercises: MutableLiveData<Resource<ExerciseResult>> = MutableLiveData()
    val exercises: MutableLiveData<Resource<ExerciseResult>> get() = _exercises

    init {
        fetchExercises()
        Timber.e("Debugging message here")
    }

    fun fetchExercises() {
        viewModelScope.launch {
            _exercises.value = repository.getExercises()
            Timber.e("Fetching message here")
        }
    }
}

