package com.example.afyamkononi.patients.exercise.retrofit

import com.example.afyamkononi.patients.exercise.model.ApiExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiExerciseService{
    @GET("/exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int = 10,
        @Query("rapidapi-key") apiKey: String = ""
    ): ApiExerciseResponse
}

