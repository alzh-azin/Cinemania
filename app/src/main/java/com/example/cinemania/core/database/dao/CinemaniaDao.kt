package com.example.cinemania.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinemania.core.database.model.MediaEntity

@Dao
interface CinemaniaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendMovies(movies: List<MediaEntity>)

    @Transaction
    @Query("SELECT * FROM MediaEntity")
    suspend fun getTrendMovies(): List<MediaEntity>

}