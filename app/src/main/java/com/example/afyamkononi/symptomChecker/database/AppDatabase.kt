package com.example.afyamkononi.symptomChecker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.afyamkononi.symptomChecker.dao.DiseaseDao
import com.example.afyamkononi.symptomChecker.model.Disease

@Database(entities = [Disease::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diseaseDao(): DiseaseDao
}
