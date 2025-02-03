package com.example.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.core.database.CinemaniaDatabase
import com.example.core.database.dao.CinemaniaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCinemaniaDatabase(app: Application): CinemaniaDatabase {
        return Room.databaseBuilder(
            app,
            CinemaniaDatabase::class.java,
            "cinemania.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCinemaniaDao(database: CinemaniaDatabase): CinemaniaDao {
        return database.cinemaniaDao()
    }
}