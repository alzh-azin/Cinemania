package com.example.cinemania.feature.home

import androidx.compose.runtime.Stable
import com.example.cinemania.core.domain.model.Media

@Stable
data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val isLoading: Boolean = false
)