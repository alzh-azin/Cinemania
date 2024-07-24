package com.example.cinemania.core.domain.repository

import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CinemaniaRepository {

    fun getTrendMediaLocal(): Flow<List<Media>>

    suspend fun getTrendMediaRemote(): NetworkResult<Unit>

    fun getMedia(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Media>
}