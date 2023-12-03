package com.example.afyamkononi.news.api

import com.example.afyamkononi.news.model.NewsResult
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getHealthNews(
       // @Query("country") country: String = "co",
        @Query("pageSize") pageSize: Int = 100,
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = ""
    ): NewsResult
}
