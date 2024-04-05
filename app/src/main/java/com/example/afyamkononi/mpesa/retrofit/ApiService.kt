package com.example.afyamkononi.mpesa.retrofit


import com.example.afyamkononi.mpesa.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/pay")
    fun pay(
        @Query("amount") amount: String,
        @Query("phone") phone: String
    ): Call<ApiResponse>
}
