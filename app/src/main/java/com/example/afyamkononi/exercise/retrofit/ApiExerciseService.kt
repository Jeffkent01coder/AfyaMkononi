package com.example.afyamkononi.exercise.retrofit

import com.example.afyamkononi.exercise.model.ApiExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiExerciseService{
    @GET("/exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int = 10,
        @Query("rapidapi-key") apiKey: String = "37464653damsh941e7c9227e0bf9p1b0871jsnbe2ddf38abba"
    ): ApiExerciseResponse
}