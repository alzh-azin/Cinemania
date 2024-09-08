package com.example.cinemania.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemania.core.domain.model.GenreType
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.UrlHelper.BASE_IMAGE_URL

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
)

fun MediaEntity.toMedia(imageQuality: String) = Media(
    backdropPath = "$BASE_IMAGE_URL$imageQuality" + backdropPath,
    id = id,
    overview = overview,
    posterPath = "$BASE_IMAGE_URL$imageQuality" + posterPath,
    mediaType = mediaType,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = genres?.map {
        GenreType.enumValueOf(it)
    }
)
