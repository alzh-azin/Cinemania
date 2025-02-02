package com.example.cinemania.core.network.service

import com.example.cinemania.core.network.model.CinemaniaListApiResponse
import com.example.core.network.utils.UrlHelper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CinemaniaService {

    @GET
    suspend fun getTrendMedia(
        @Url url: String = UrlHelper.TRENDING_MEDIA_API_URL
    ): Response<CinemaniaListApiResponse>

    @GET
    suspend fun searchMedia(
        @Url url: String = UrlHelper.SEARCH,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<CinemaniaListApiResponse>
}