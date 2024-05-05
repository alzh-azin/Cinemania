package com.example.cinemania.core.network.model

import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.UrlHelper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaNetwork(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "media_type")
    val mediaType: String?,
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "video")
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?
)

fun MediaNetwork.toMedia() = Media(
    backdropPath = UrlHelper.BASE_IMAGE_URL + backdropPath,
    id = id,
    overview = overview,
    posterPath = UrlHelper.BASE_IMAGE_URL + posterPath,
    mediaType = mediaType,
    adult = adult,
    title = when (mediaType) {
        MediaTypeNetwork.MOVIE.value -> title
        MediaTypeNetwork.TV_SHOW.value -> name
        else -> ""
    },
    originalLanguage = originalLanguage,
    popularity = popularity,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)
