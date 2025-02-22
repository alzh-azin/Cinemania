package com.example.core.database.dao

import androidx.room.withTransaction
import com.example.core.database.CinemaniaDatabase
import com.example.core.database.model.FilterTypeEntity
import com.example.core.database.model.MediaEntity
import com.example.core.database.model.getCategory
import com.example.core.database.model.toMediaFilterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaLocalDataSource @Inject constructor(
    private val cinemaniaDao: CinemaniaDao,
    private val cinemaniaDatabase: CinemaniaDatabase
) {

    suspend fun insertTrendMovies(mediaList: List<MediaEntity>) {
        cinemaniaDatabase.withTransaction {

            cinemaniaDao.insertMediaFilterList(
                mediaList.mapIndexed { index, media ->
                    media.toMediaFilterEntity(
                        filterTypeEntity = FilterTypeEntity.TrendMedia,
                        index = index
                    )
                }
            )

            cinemaniaDao.insertMediaList(mediaList)
        }
    }

    suspend fun insertTrendMediaByGenre(
        genreName: String,
        mediaList: List<MediaEntity>
    ) {

        cinemaniaDao.insertMediaFilterList(mediaList.mapIndexed { index, media ->
            media.toMediaFilterEntity(
                filterTypeEntity = FilterTypeEntity.TrendMediaByGenre,
                genreName = genreName,
                index = index
            )
        })

        cinemaniaDao.insertMediaList(mediaList)
    }

    suspend fun insertMedia(media: MediaEntity) {
        cinemaniaDao.insertMedia(media)
    }

    fun getMedia(id: Int) =
        cinemaniaDao.getMedia(id)

    fun getMediaByFilterType(filterType: FilterTypeEntity, genreName: String) =
        cinemaniaDao.getMediaByFilterType(getCategory(filterType, genreName))
}