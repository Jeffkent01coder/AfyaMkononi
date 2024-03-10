package com.example.afyamkononi.exercise.repository


import com.example.afyamkononi.exercise.exercise.api.ApiService
import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class ExerciseDataRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getExercises(limit: Int, apiKey: String): List<ExerciseModelItem>? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getExercises(limit, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
