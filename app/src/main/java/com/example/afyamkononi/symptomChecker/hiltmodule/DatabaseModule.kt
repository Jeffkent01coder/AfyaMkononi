package com.example.afyamkononi.symptomChecker.hiltmodule

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.afyamkononi.symptomChecker.dao.DiseaseDao
import com.example.afyamkononi.symptomChecker.database.AppDatabase
import com.example.afyamkononi.symptomChecker.seeder.DatabaseSeeder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideDiseaseDao(database: AppDatabase): DiseaseDao {
        return database.diseaseDao()
    }

    @Provides
    fun provideDatabaseSeeder(diseaseDao: DiseaseDao, context: Context): DatabaseSeeder {
        return DatabaseSeeder(diseaseDao, context)
    }
}
