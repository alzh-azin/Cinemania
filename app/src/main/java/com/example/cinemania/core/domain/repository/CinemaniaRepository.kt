package com.example.cinemania.core.domain.repository

import com.example.cinemania.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface CinemaniaRepository {

    fun getTrendingMedia(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ): Flow<List<Media>>

    fun getMedia(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Media>
}