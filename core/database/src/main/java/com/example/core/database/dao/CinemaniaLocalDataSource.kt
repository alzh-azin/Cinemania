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

    suspend fun insertMedia(media: MediaEntity) {
        cinemaniaDao.insertMedia(media)
    }

    suspend fun insertMedia(
        filterType: FilterTypeEntity,
        genreName: String? = null,
        mediaList: List<MediaEntity>
    ) {

        cinemaniaDatabase.withTransaction {

            cinemaniaDao.deleteMediaByGenre(
                getCategory(
                    filterTypeEntity =filterType,
                    genreName = genreName
                )
            )

            cinemaniaDao.insertMediaFilterList(mediaList.mapIndexed { index, media ->
                media.toMediaFilterEntity(
                    filterTypeEntity = filterType,
                    genreName = genreName,
                    index = index
                )
            })

            cinemaniaDao.insertMediaList(mediaList)
        }
    }

    fun getMedia(id: Int) =
        cinemaniaDao.getMedia(id)

    fun getMedia(filterType: FilterTypeEntity, genreName: String) =
        cinemaniaDao.getMediaByFilterType(getCategory(filterType, genreName))
}