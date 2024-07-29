package com.example.cinemania.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemania.core.domain.model.GenreType
import com.example.cinemania.core.domain.model.Media

@Entity
data class MediaEntity(
    val backdropPath: String?,
    @PrimaryKey
    val id: Int,
    val overview: String,
    val posterPath: String,
    val mediaType: String,
    val title: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val isTrendMedia: Boolean,
    val genres: List<Int>?,
    val index: Int,
)

fun MediaEntity.toMedia() = Media(
    backdropPath = backdropPath,
    id = id,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = genres?.map {
        GenreType.enumValueOf(it)
    }
)
