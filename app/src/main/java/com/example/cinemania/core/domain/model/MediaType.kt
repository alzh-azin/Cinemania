package com.example.cinemania.core.domain.model

enum class MediaType(val value: String, val typeName: String) {
    MOVIE("movie", "Movie"),
    TV_SHOW("tv", "TV Show");


    companion object {
        fun toMediaType(type: String?): MediaType {
            return when (type) {
                MOVIE.value -> MOVIE
                TV_SHOW.value -> TV_SHOW
                else -> MOVIE
            }
        }
    }
}
