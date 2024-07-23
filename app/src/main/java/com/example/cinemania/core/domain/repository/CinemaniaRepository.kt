package com.example.cinemania.core.domain.repository

import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CinemaniaRepository {

    fun getTrendingMedia(): Flow<NetworkResult<List<Media>>>

    fun getMedia(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Media>
}