package com.example.cinemania.core.network.model

import com.squareup.moshi.Json

data class CinemaniaListApiResponse(
    val page: Int,
    val results: List<MediaNetwork>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)