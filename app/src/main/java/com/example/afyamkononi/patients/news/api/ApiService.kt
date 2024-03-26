package com.example.afyamkononi.patients.news.api

import com.example.afyamkononi.patients.news.model.NewsResult
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getHealthNews(
        //40d44753106d4584929803ce5e09cb98
       // @Query("country") country: String = "co",
        @Query("pageSize") pageSize: Int = 100,
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = ""
    ): NewsResult
}
