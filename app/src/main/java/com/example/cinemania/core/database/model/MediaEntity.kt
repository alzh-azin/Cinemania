package com.example.cinemania.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemania.core.domain.model.Media

@Entity
data class MediaEntity(
    val backdropPath: String,
    @PrimaryKey
    val id: Int,
    val overview: String,
    val posterPath: String,
    val mediaType: String,
    val adult: Boolean,
    val title: String,
    val originalLanguage: String,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

fun MediaEntity.toMedia() = Media(
    backdropPath = backdropPath,
    id = id,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    adult = adult,
    title = title,
    originalLanguage = originalLanguage,
    popularity = popularity,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)
