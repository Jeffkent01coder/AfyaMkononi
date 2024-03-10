package com.example.afyamkononi.exercise.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem
import com.example.afyamkononi.exercise.repository.ExerciseDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseViewModel @Inject constructor(private val repository: ExerciseDataRepository) :
    ViewModel() {

    private val _exercises = MutableLiveData<List<ExerciseModelItem>?>()
    val exercises: MutableLiveData<List<ExerciseModelItem>?>
        get() = _exercises

    fun fetchExercises(limit: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getExercises(limit, apiKey)
            _exercises.postValue(result)
        }
    }
}

