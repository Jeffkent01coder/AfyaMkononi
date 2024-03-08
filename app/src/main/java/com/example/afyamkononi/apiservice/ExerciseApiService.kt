package com.example.afyamkononi.apiservice

import com.example.afyamkononi.models.ExerciseModelItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
interface ExerciseApiService {
    @GET("exercises/bodyPart/back")
    fun getExercises(
        @Query("limit") limit: Int,
        @Query("X-RapidAPI-Key") apiKey: String
    ): Call<List<ExerciseModelItem>>
}

object RetrofitClient {
    private const val BASE_URL = "https://exercisedb.p.rapidapi.com/"

    fun create(): ExerciseApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ExerciseApiService::class.java)
    }
}
