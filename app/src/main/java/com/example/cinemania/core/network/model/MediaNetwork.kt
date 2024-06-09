package com.example.cinemania.core.network.model

import com.example.cinemania.core.database.model.MediaEntity
import com.example.cinemania.core.domain.model.Genre
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.UrlHelper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaNetwork(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "media_type")
    val mediaType: String,
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

fun MediaNetwork.toMedia() = Media(
    backdropPath = UrlHelper.BASE_IMAGE_URL + backdropPath,
    id = id,
    overview = overview,
    posterPath = UrlHelper.BASE_IMAGE_URL + posterPath,
    mediaType = mediaType,
    title = when (mediaType) {
        MediaTypeNetwork.MOVIE.value -> title
        MediaTypeNetwork.TV_SHOW.value -> name
        else -> ""
    },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = genres?.map {
        Genre.enumValueOf(it)
    }
)

fun MediaNetwork.toMediaEntity(isTrendMedia: Boolean) = MediaEntity(
    backdropPath = UrlHelper.BASE_IMAGE_URL + backdropPath,
    id = id,
    overview = overview,
    posterPath = UrlHelper.BASE_IMAGE_URL + posterPath,
    mediaType = when (mediaType) {
        MediaTypeNetwork.MOVIE.value -> MediaTypeNetwork.MOVIE.typeName
        MediaTypeNetwork.TV_SHOW.value -> MediaTypeNetwork.TV_SHOW.typeName
        else -> ""
    },
    title = when (mediaType) {
        MediaTypeNetwork.MOVIE.value -> title
        MediaTypeNetwork.TV_SHOW.value -> name
        else -> ""
    },
    releaseDate = when (mediaType) {
        MediaTypeNetwork.MOVIE.value -> releaseDate
        MediaTypeNetwork.TV_SHOW.value -> firstAirDate
        else -> ""
    },
    voteAverage = voteAverage,
    isTrendMedia = isTrendMedia,
    genres = genres
)
