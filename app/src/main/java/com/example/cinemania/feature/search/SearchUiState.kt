package com.example.cinemania.feature.search

import androidx.compose.runtime.Stable
import com.example.cinemania.core.domain.model.Media

@Stable
data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: List<Media> = emptyList(),
    val emptyResult: Boolean = false,
    val isLoading: Boolean = false
)
