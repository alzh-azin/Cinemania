package com.example.feature.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.PagingData
import com.example.core.domain.model.Media
import com.example.core.domain.model.MediaType
import kotlinx.coroutines.flow.flowOf

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

private val sampleMedia = Media(
    backdropPath = null,
    id = 42,
    overview = "A thrilling preview of Compose.",
    posterPath = "/sample.jpg",
    mediaType = MediaType.MOVIE,
    title = "Compose Adventures",
    releaseDate = "2025-01-01",
    voteAverage = 8.7,
    genres = listOf("Action", "UI")
)

class SearchUiStatePreviewProvider : PreviewParameterProvider<SearchUiState> {
    override val values = sequenceOf(
        // Empty query, no loading
        SearchUiState(
            searchQuery = "",
            searchResult = null,
            isLoading = false,
            isLoadingNextPage = false,
            showPaginationError = false
        ),
        // Loading state
        SearchUiState(
            searchQuery = "",
            searchResult = null,
            isLoading = true,
            isLoadingNextPage = false,
            showPaginationError = false
        ),
        // Some results
        SearchUiState(
            searchQuery = "Compose",
            searchResult = flowOf(PagingData.from(listOf(sampleMedia))),
            isLoading = false,
            isLoadingNextPage = false,
            showPaginationError = false
        )
    )
}