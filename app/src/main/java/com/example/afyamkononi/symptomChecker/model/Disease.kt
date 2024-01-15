package com.example.afyamkononi.symptomChecker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diseases")
data class Disease(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val symptoms: List<String>
)

