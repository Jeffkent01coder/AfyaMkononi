package com.example.afyamkononi.patients.ai.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("completions")
    fun getCompletion(
        @Header("Authorization") authorizationHeader: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>
}