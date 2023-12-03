package com.example.afyamkononi.repository

import com.example.afyamkononi.news.api.ApiService
import com.example.afyamkononi.news.model.NewsResult
import com.example.afyamkononi.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsDataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getHealthNews(): Resource<NewsResult> {
        return try {
            val remoteOrganizationResult = apiService.getHealthNews()
            Resource.Success<NewsResult>(data = remoteOrganizationResult)
        } catch (e: IOException) {
            Resource.Error<NewsResult>(message = "Check your internet connection!")

        } catch (e: HttpException) {
            Resource.Error<NewsResult>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }
}