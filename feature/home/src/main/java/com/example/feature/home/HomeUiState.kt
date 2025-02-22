package com.example.feature.home

import androidx.compose.runtime.Stable
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.GenreType.Companion.DEFAULT_GENRE
import com.example.core.domain.model.GenreType.Companion.generateGenreList
import com.example.core.domain.model.Media

@Stable
data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val trendMediaByGenre : List<Media> = emptyList(),
    val genreList: List<GenreType> = generateGenreList(),
    val selectedGenre : GenreType= DEFAULT_GENRE,
    val isLoading: Boolean = false
)