package com.example.core.ui.model

import androidx.compose.runtime.Immutable
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType

@Immutable
data class MediaUi(
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
){
    fun toMedia() = Media(
        backdropPath = backdropPath,
        id = id,
        index =index,
        overview = overview,
        posterPath = posterPath,
        mediaType =mediaType,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genres = genres

    )
}

fun Media.toMediaUi() = MediaUi(
    backdropPath = backdropPath,
    id = id,
    index = index,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = genres
)

