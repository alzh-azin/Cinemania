package com.example.core.network.service

import com.example.core.common.result.NetworkResult
import com.example.core.network.model.CinemaniaListApiResponse
import com.example.core.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaniaRemoteDataSource @Inject constructor(
    private val cinemaniaService: CinemaniaService
) {

    suspend fun getTrendingMedia(): NetworkResult<CinemaniaListApiResponse> =
        safeApiCall(
            call = {
                cinemaniaService.getTrendMedia()
            },
            exceptionMessage = "Error getting trend media"
        )

    suspend fun searchMedia(query: String, page: Int): NetworkResult<CinemaniaListApiResponse> =
        safeApiCall(
            call = {
                cinemaniaService.searchMedia(query = query, page = page)
            },
            exceptionMessage = "Error searching media"
        )
}