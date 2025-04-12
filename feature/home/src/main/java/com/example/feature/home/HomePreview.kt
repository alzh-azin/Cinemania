package com.example.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType

class PreviewMediaProvider : PreviewParameterProvider<Media> {
    override val values = sequenceOf(
        Media(
            id = 1,
            title = "Inception",
            overview = "A mind-bending sci-fi thriller",
            mediaType = MediaType.MOVIE,
            backdropPath = "/sample.jpg",
            posterPath = "/poster.jpg",
            releaseDate = "2010-07-16",
            voteAverage = 8.8,
            genres = listOf("Sci-Fi", "Action"),
            index = 0
        )
    )
}

class PreviewHomeUiStateProvider : PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(
        HomeUiState(
            isLoading = false,
            trendMedia = List(5) {
                Media(
                    id = it,
                    title = "Movie $it",
                    posterPath = "/poster$it.jpg",
                    backdropPath = "/backdrop$it.jpg",
                    overview = "Description of Movie $it",
                    mediaType = MediaType.MOVIE,
                    releaseDate = "2023-0${(it + 1)}-01",
                    voteAverage = 7.5 + it,
                    genres = listOf("Action", "Drama"),
                    index = it
                )
            },
            genreList = listOf(GenreType.Action, GenreType.Comedy, GenreType.Drama),
            selectedGenre = GenreType.Action,
            trendMediaByGenre = List(3) {
                Media(
                    id = it + 100,
                    title = "Genre Movie $it",
                    posterPath = "/genre$it.jpg",
                    backdropPath = null,
                    overview = null,
                    mediaType = MediaType.MOVIE,
                    releaseDate = null,
                    voteAverage = null,
                    genres = null,
                    index = it
                )
            }
        )
    )
}
