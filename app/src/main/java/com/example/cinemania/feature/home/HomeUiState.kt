package com.example.cinemania.feature.home

import androidx.compose.runtime.Stable
import com.example.cinemania.core.domain.model.Genre
import com.example.cinemania.core.domain.model.GenreType
import com.example.cinemania.core.domain.model.Media

@Stable
data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val genreList: List<Genre> = GenreType.getGenreList(),
    val isLoading: Boolean = false
)