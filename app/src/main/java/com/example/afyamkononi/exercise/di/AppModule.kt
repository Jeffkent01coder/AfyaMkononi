package com.example.afyamkononi.exercise.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.afyamkononi.exercise.exercise.api.ApiService
import com.example.afyamkononi.exercise.repository.ExerciseDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(application: Application): ViewModelProvider.Factory {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideExerciseDataRepository(apiService: ApiService): ExerciseDataRepository {
        return ExerciseDataRepository(apiService)
    }

    private const val BASE_URL = "https://exercisedb.p.rapidapi.com/"
}
