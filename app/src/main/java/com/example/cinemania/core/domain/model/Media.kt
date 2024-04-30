package com.example.cinemania.core.domain.model

data class Media(
    val backdropPath: String?,
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val adult: Boolean?,
    val title: String?,
    val originalLanguage: String?,
    val popularity: Double?,
    val releaseDate: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
)
