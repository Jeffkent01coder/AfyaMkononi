package com.example.afyamkononi.patients.machineLearning.retrofit

import com.example.afyamkononi.patients.machineLearning.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ApiResponse>
}
