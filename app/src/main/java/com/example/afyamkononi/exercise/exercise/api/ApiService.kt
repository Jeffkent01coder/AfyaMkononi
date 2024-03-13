package com.example.afyamkononi.exercise.exercise.api

import com.example.afyamkononi.exercise.exercise.model.ExerciseResult
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("exercises/bodyPart/back")
    suspend fun getExercises(
//        @Query("limit") limit: String,
        @Query("X-RapidAPI-Key") apiKey: String = ""
    ): ExerciseResult
}

//37464653damsh941e7c9227e0bf9p1b0871jsnbe2ddf38abba
