package com.example.cinemania.core.database.dao

import com.example.cinemania.core.database.model.MediaEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaLocalDataSource @Inject constructor(private val cinemaniaDao: CinemaniaDao) {

    suspend fun insertTrendMovies(movies: List<MediaEntity>) =
        cinemaniaDao.insertTrendMovies(movies)

    suspend fun getTrendMovies() =
        cinemaniaDao.getTrendMovies()
}