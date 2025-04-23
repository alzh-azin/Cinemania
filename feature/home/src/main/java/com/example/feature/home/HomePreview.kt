package com.example.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType

class MediaPreviewProvider : PreviewParameterProvider<Media> {
    override val values = sequenceOf(
        Media(
            backdropPath = "/backdrop.jpg",
            id = 1,
            index = 0,
            overview = "This is a preview overview of the movie.",
            posterPath = "/poster.jpg",
            mediaType = MediaType.MOVIE,
            title = "Preview Movie Title",
            releaseDate = "2024-01-01",
            voteAverage = 8.5,
            genres = listOf("Action", "Adventure")
        )
    )
}

class MediaListPreviewProvider : PreviewParameterProvider<List<Media>> {
    override val values = sequenceOf(
        listOf(
            Media(
                backdropPath = "/backdrop1.jpg",
                id = 1,
                index = 0,
                overview = "Movie 1 description.",
                posterPath = "/poster1.jpg",
                mediaType = MediaType.MOVIE,
                title = "Movie One",
                releaseDate = "2024-01-01",
                voteAverage = 7.2,
                genres = listOf("Action")
            ),
            Media(
                backdropPath = "/backdrop2.jpg",
                id = 2,
                index = 1,
                overview = "Movie 2 description.",
                posterPath = "/poster2.jpg",
                mediaType = MediaType.MOVIE,
                title = "Movie Two",
                releaseDate = "2024-02-01",
                voteAverage = 8.0,
                genres = listOf("Drama")
            ),
            Media(
                backdropPath = "/backdrop3.jpg",
                id = 3,
                index = 2,
                overview = "Movie 3 description.",
                posterPath = "/poster3.jpg",
                mediaType = MediaType.MOVIE,
                title = "Movie Three",
                releaseDate = "2024-02-01",
                voteAverage = 8.0,
                genres = listOf("Comedy")
            )
        )
    )
}

class GenreTypePreviewProvider : PreviewParameterProvider<GenreType> {
    override val values = sequenceOf(
        GenreType.Action
    )
}

class GenreListPreviewProvider : PreviewParameterProvider<List<GenreType>> {
    override val values = sequenceOf(
        listOf(
            GenreType.Action,
            GenreType.Comedy,
            GenreType.Drama,
            GenreType.ScienceFiction
        )
    )
}

class HomeUiStatePreviewProvider : PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(
        HomeUiState(
            trendMedia = MediaListPreviewProvider().values.first(),
            trendMediaByGenre = MediaListPreviewProvider().values.first(),
            genreList = GenreListPreviewProvider().values.first(),
            selectedGenre = GenreType.Comedy,
            isLoading = false
        ),
        HomeUiState(
            trendMedia = emptyList(),
            trendMediaByGenre = emptyList(),
            genreList = GenreListPreviewProvider().values.first(),
            selectedGenre = GenreType.Action,
            isLoading = true
        )
    )
}
