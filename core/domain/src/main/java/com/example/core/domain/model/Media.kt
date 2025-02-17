package com.example.core.domain.model

//@Stable
data class Media(
    val backdropPath: String?,
    val id: Int,
    val index: Int = 0,
    val overview: String?,
    val posterPath: String?,
    val mediaType: MediaType?,
    val title: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val genres: List<String>?,
    val isTrendMedia: Boolean? = null,
)
