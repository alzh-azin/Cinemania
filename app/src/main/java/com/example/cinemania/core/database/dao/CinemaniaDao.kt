package com.example.cinemania.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinemania.core.database.model.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaniaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(movies: List<MediaEntity>)

    @Transaction
    @Query("SELECT * FROM MediaEntity")
    suspend fun getMediaList(): List<MediaEntity>

    @Transaction
    @Query("SELECT * FROM MediaEntity WHERE isTrendMedia = 1 ORDER BY `index` ASC")
    fun getMediaTrendList(): Flow<List<MediaEntity>>

    @Transaction
    @Query("SELECT * FROM MediaEntity WHERE id = :id")
    suspend fun getMedia(id: Int): MediaEntity

    @Transaction
    @Query("DELETE FROM MediaEntity WHERE isTrendMedia = 1")
    suspend fun deleteTrendMedia()

}