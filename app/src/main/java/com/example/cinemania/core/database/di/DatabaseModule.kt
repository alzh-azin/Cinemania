package com.example.cinemania.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.cinemania.core.database.CinemaniaDatabase
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
}