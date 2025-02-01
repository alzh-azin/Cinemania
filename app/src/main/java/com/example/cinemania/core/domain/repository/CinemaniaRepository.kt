package com.example.cinemania.core.domain.repository

import androidx.paging.PagingData
import com.example.cinemania.core.network.utils.NetworkResult
import com.example.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface CinemaniaRepository {

    fun getTrendMediaLocal(): Flow<List<Media>>

    suspend fun getTrendMediaRemote(): NetworkResult<Unit>

    suspend fun searchMediaRemote(query: String, pageSize: Int): Flow<PagingData<Media>>

    fun getMedia(id: Int): Flow<Media>

    suspend fun insertMedia(media: Media)
}