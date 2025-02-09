package com.example.feature.home

import androidx.compose.runtime.Stable
import com.example.core.domain.model.Genre
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media

@Stable
data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val genreList: List<Genre> = GenreType.getGenreList(),
    val isLoading: Boolean = false
)