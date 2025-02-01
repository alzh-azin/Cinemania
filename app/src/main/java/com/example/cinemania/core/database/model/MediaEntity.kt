package com.example.cinemania.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType


@Entity
data class MediaEntity(
    val backdropPath: String?,
    @PrimaryKey
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val title: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val isTrendMedia: Boolean?,
    val genres: List<Int>?,
    val index: Int,
) {
    fun toMedia() = Media(
        backdropPath = backdropPath,
        id = id,
        overview = overview,
        posterPath = posterPath,
        mediaType = MediaType.toMediaType(mediaType),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genres = genres?.map {
            GenreType.enumValueOf(it)
        }
    )
}

fun Media.toMediaEntity() = MediaEntity(
    backdropPath = backdropPath,
    id = id,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType?.value,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    isTrendMedia = false,
    genres = genres?.map {
        GenreType.getGenreCode(it, mediaType)
    },
    index = index

)

