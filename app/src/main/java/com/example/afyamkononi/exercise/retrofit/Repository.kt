package com.example.afyamkononi.exercise.retrofit

import com.example.afyamkononi.exercise.model.Exercise
import com.example.afyamkononi.exercise.util.Resource
import retrofit2.HttpException
import java.io.IOException

class Repository {
    suspend fun getExercises(): Resource<List<Exercise>> {
        return try {
            val apiResponse = RetrofitInstance.api.getExercises()
            Resource.Success<List<Exercise>>(data = apiResponse)
        } catch (e: IOException) {
            Resource.Error<List<Exercise>>(message = "Check your internet connection!")

        } catch (e: HttpException) {
            Resource.Error<List<Exercise>>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }
}