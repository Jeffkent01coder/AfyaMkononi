package com.example.afyamkononi.exercise.exercise.api

import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("exercises/bodyPart/back")
    suspend fun getExercises(
        @Query("limit") limit: Int,
        @Query("X-RapidAPI-Key") apiKey: String
    ): List<ExerciseModelItem>
}
