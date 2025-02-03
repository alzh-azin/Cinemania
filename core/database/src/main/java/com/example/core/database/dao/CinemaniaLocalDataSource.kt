package com.example.core.database.dao

import androidx.room.withTransaction
import com.example.core.database.CinemaniaDatabase
import com.example.core.database.model.MediaEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaLocalDataSource @Inject constructor(
    private val cinemaniaDao: CinemaniaDao,
    private val cinemaniaDatabase: CinemaniaDatabase
) {

    suspend fun insertTrendMovies(movies: List<MediaEntity>) {
        cinemaniaDatabase.withTransaction {
            cinemaniaDao.deleteTrendMedia()
            cinemaniaDao.insertMediaList(movies)
        }
    }

    suspend fun insertMedia(media: MediaEntity) {
        cinemaniaDao.insertMedia(media)
    }

    fun getTrendMovies() =
        cinemaniaDao.getMediaTrendList()

    fun getMedia(id: Int) =
        cinemaniaDao.getMedia(id)
}