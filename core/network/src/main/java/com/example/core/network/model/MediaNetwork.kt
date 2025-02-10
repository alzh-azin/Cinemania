package com.example.core.network.model

import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class
MediaNetwork(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val index: Int = 0,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "media_type")
    val mediaType: String?,
    val title: String?,
    val name: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "genre_ids")
    val genres: List<Int>?
)

fun MediaNetwork.toMedia(
    isTrendMedia: Boolean = false,
    index: Int = 0
) = Media(
    backdropPath = backdropPath,
    id = id,
    overview = overview,
    posterPath = posterPath,
    mediaType = MediaType.toMediaType(mediaType),
    title = when (mediaType) {
        MediaType.MOVIE.value -> title
        MediaType.TV_SHOW.value -> name
        else -> ""
    },
    releaseDate = when (mediaType) {
        MediaType.MOVIE.value -> releaseDate
        MediaType.TV_SHOW.value -> firstAirDate
        else -> ""
    },
    voteAverage = voteAverage,
    genres = genres?.map {
        GenreType.enumValueOf(it)
    },
    index = index,
    isTrendMedia = isTrendMedia
)
