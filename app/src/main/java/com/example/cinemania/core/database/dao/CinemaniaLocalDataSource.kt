package com.example.cinemania.core.database.dao

import androidx.room.withTransaction
import com.example.cinemania.core.database.CinemaniaDatabase
import com.example.cinemania.core.database.model.MediaEntity
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
            cinemaniaDao.insertMedia(movies)
        }
    }


    suspend fun getTrendMovies() =
        cinemaniaDao.getMediaList()

    suspend fun getMedia(id: Int) =
        cinemaniaDao.getMedia(id)
}