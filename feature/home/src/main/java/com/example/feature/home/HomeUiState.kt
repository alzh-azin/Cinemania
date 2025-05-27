package com.example.feature.home

import androidx.compose.runtime.Immutable
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.GenreType.Companion.DEFAULT_GENRE
import com.example.core.domain.model.GenreType.Companion.generateGenreList
import com.example.core.ui.model.MediaUi

@Immutable
data class HomeUiState(
    val trendMedia: List<MediaUi> = emptyList(),
    val trendMediaByGenre : List<MediaUi> = emptyList(),
    val genreList: List<GenreType> = generateGenreList(),
    val selectedGenre : GenreType= DEFAULT_GENRE,
    val isLoading: Boolean = false
)