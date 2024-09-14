package com.example.cinemania.core.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Media(
    val backdropPath: String?,
    val id: Int,
    val index: Int = 0,
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val title: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val genres: List<String>?
)
