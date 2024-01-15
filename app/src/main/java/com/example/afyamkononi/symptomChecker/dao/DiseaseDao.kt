package com.example.afyamkononi.symptomChecker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.afyamkononi.symptomChecker.model.Disease

@Dao
interface DiseaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(diseases: List<Disease>)
}
