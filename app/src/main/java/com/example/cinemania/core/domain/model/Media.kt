package com.example.cinemania.core.domain.model

data class Media(
    val backdropPath: String?,
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val title: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
)
