package com.example.cinemania.core.network.service

import com.example.cinemania.core.network.model.CinemaniaListApiResponse
import com.example.cinemania.core.network.utils.NetworkResult
import com.example.cinemania.core.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaRemoteDataSource @Inject constructor(
    private val cinemaniaService: CinemaniaService
) {

    suspend fun getTrendingMedia(): NetworkResult<CinemaniaListApiResponse> = safeApiCall(
        call = {
            cinemaniaService.getTrendMedia()
        },
        exceptionMessage = "Error getting trend media"
    )
}