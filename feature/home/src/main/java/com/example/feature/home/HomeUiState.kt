package com.example.feature.home

import androidx.compose.runtime.Stable
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.GenreType.Companion.generateGenreMap
import com.example.core.domain.model.Media

@Stable
data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val trendMediaByGenre : List<Media> = emptyList(),
    val genreList: Map<GenreType, Boolean> = generateGenreMap(),
    val isLoading: Boolean = false
)