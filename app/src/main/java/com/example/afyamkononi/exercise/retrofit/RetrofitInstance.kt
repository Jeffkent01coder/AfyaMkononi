package com.example.afyamkononi.exercise.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set the custom OkHttpClient with interceptors
            .build()
    }

    val api: ApiExerciseService by lazy {
        retrofit.create(ApiExerciseService::class.java)
    }

}