package com.example.afyamkononi.exercise.repository


import com.example.afyamkononi.exercise.exercise.api.ApiService
import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem
import com.example.afyamkononi.exercise.exercise.model.ExerciseResult
import com.example.afyamkononi.exercise.util.Constants
import com.example.afyamkononi.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExerciseDataRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getExercises(): Resource<ExerciseResult>? {
        return try {
            val remoteOrganizationResult = apiService.getExercises(Constants.API_KEY)
            Resource.Success<ExerciseResult>(data = remoteOrganizationResult)
        } catch (e: IOException) {
            Resource.Error<ExerciseResult>(message = "Check your internet connection!")
        } catch (e: HttpException) {
            Resource.Error<ExerciseResult>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }


    }
}
