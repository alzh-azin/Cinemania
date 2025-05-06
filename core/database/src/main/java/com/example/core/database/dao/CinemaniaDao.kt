package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.core.database.model.MediaEntity
import com.example.core.database.model.MediaFilterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaniaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaList(media: List<MediaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaFilterList(mediaFilter : List<MediaFilterEntity>)

    @Transaction
    @Query("SELECT * FROM MediaEntity WHERE id = :id")
    fun getMedia(id: Int): Flow<MediaEntity>

    @Transaction
    @Query(
        """
        SELECT * FROM MediaEntity 
        WHERE id IN (SELECT id FROM MediaFilterEntity WHERE category = :filterType)
    """
    )
     fun getMediaByFilterType(filterType: String): Flow<List<MediaEntity>>

     @Transaction
     @Query("DELETE FROM MediaFilterEntity WHERE category = :filterType")
    suspend fun deleteMediaByGenre(filterType: String)
}