package com.example.afyamkononi.symptomChecker.seeder

import android.content.Context
import com.example.afyamkononi.symptomChecker.dao.DiseaseDao
import com.example.afyamkononi.symptomChecker.model.Disease
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val diseaseDao: DiseaseDao,
    private val context: Context
) {
    suspend fun seedDatabase() {
        val jsonString = context.assets.open("diseases_data.json").bufferedReader().use {
            it.readText()
        }
        val diseases : List<Disease> = Gson().fromJson(jsonString, object : TypeToken<List<Disease>>() {}.type)
        diseaseDao.insertAll(diseases)
    }
}
