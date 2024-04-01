package com.example.afyamkononi.shared.fire.notification.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("registerDevice") // Endpoint on your Node.js backend
    fun registerDevice(@Body token: String): Call<Void>
}
