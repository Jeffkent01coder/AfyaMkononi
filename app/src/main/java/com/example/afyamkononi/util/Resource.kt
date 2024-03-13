package com.example.afyamkononi.util

import com.example.afyamkononi.exercise.exercise.model.ExerciseModelItem

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T? = null, message: String): Resource<T>(data, message)
    class Loading<T>: Resource<T>()
}
