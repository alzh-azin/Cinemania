package com.example.feature.details

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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