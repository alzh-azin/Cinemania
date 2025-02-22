package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.common.result.NetworkResult
import com.example.core.domain.model.FilterType
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface CinemaniaRepository {

    suspend fun getTrendMediaRemote(): NetworkResult<Unit>

    suspend fun getTrendMoviesByGenreRemote(genre: GenreType): NetworkResult<Unit>

    fun getMedia(id: Int): Flow<Media>

    fun getMediaByFilterTypeLocal(genre: GenreType? = null, filterType: FilterType): Flow<List<Media>>

    suspend fun searchMediaRemote(query: String, pageSize: Int): Flow<PagingData<Media>>

    suspend fun insertMedia(media: Media)
}